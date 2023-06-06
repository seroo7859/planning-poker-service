package de.bht.planningpoker.event;

import de.bht.planningpoker.model.User;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
@EqualsAndHashCode(callSuper = true)
public class UserDisconnectedEvent extends ApplicationEvent {
    User user;

    public UserDisconnectedEvent(User user) {
        super(user);
        this.user = user;
    }
}
