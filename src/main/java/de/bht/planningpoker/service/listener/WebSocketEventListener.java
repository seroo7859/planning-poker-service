package de.bht.planningpoker.service.listener;

import de.bht.planningpoker.auth.CustomUserDetails;
import de.bht.planningpoker.event.UserConnectedEvent;
import de.bht.planningpoker.event.UserDisconnectedEvent;
import de.bht.planningpoker.model.User;
import de.bht.planningpoker.repository.UserRepository;
import de.bht.planningpoker.service.exception.ServiceErrorCode;
import de.bht.planningpoker.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataAccessException;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final UserRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    @EventListener
    @Transactional
    public void handleSessionConnect(SessionConnectEvent event) throws ServiceException {
        log.info("Received a new web socket connection.");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Map<String, Object> attributes = Objects.requireNonNull(accessor.getSessionAttributes());
        attributes.put("user_id", user.getId());

        try {
            user.setActive(true);
            repository.save(user);
            eventPublisher.publishEvent(new UserConnectedEvent(user));
        } catch(DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.UPDATE_ERROR, e);
        }
    }

    @EventListener
    @Transactional
    public void handleSessionDisconnect(SessionDisconnectEvent event) throws ServiceException {
        log.info("Disconnected a web socket connection.");

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Map<String, Object> attributes = Objects.requireNonNull(accessor.getSessionAttributes());
        Long userId = (Long) attributes.get("user_id");

        try {
            repository.findById(userId).ifPresent(user -> {
                user.setActive(false);
                repository.save(user);
                eventPublisher.publishEvent(new UserDisconnectedEvent(user));
            });
        } catch (DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.UPDATE_ERROR, e);
        }
    }

}
