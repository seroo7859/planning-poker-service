package de.bht.planningpoker.event.publisher;

import de.bht.planningpoker.event.UserConnectedEvent;
import de.bht.planningpoker.event.UserDisconnectedEvent;
import de.bht.planningpoker.event.UserJoinedEvent;
import de.bht.planningpoker.event.UserLeavedEvent;
import de.bht.planningpoker.model.User;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishUserConnectedEvent(User user) {
        eventPublisher.publishEvent(new UserConnectedEvent(user));
    }

    public void publishUserDisconnectedEvent(User user) {
        eventPublisher.publishEvent(new UserDisconnectedEvent(user));
    }

    public void publishUserJoinedEvent(User user) {
        eventPublisher.publishEvent(new UserJoinedEvent(user));
    }

    public void publishUserLeavedEvent(User user) {
        eventPublisher.publishEvent(new UserLeavedEvent(user));
    }

}
