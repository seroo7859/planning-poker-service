package de.bht.planningpoker.event.listener;

import de.bht.planningpoker.controller.mapper.UserMapper;
import de.bht.planningpoker.event.UserConnectedEvent;
import de.bht.planningpoker.event.UserDisconnectedEvent;
import de.bht.planningpoker.event.UserJoinedEvent;
import de.bht.planningpoker.event.UserLeavedEvent;
import de.bht.planningpoker.model.Session;
import de.bht.planningpoker.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserListener {

    private final UserMapper mapper;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, condition = "#event.user.team.hasMember(event.user.username)")
    public void handleUserJoined(UserJoinedEvent event) {
        User user = event.getUser();
        Session session = user.getTeam().getSession();

        log.info("User {} joined the session {}", user.getUsername(), session.getPublicId());
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/team/member-joined", session.getPublicId()), mapper.mapToDto(user));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserLeaved(UserLeavedEvent event) {
        User user = event.getUser();
        Session session = user.getTeam().getSession();

        log.info("User {} leaved the session {}", user.getUsername(), session.getPublicId());
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/team/member-leaved", session.getPublicId()), mapper.mapToDto(user));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, condition = "#event.user.active")
    public void handleUserConnected(UserConnectedEvent event) {
        User user = event.getUser();
        Session session = user.getTeam().getSession();

        log.info("User {} connected to session {}", user.getUsername(), session.getPublicId());
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/team/member-connected", session.getPublicId()), mapper.mapToDto(user));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, condition = "!#event.user.active")
    public void handleUserDisconnected(UserDisconnectedEvent event) {
        User user = event.getUser();
        Session session = user.getTeam().getSession();

        log.info("User {} disconnected to session {}", user.getUsername(), session.getPublicId());
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/team/member-disconnected", session.getPublicId()), mapper.mapToDto(user));
    }

}
