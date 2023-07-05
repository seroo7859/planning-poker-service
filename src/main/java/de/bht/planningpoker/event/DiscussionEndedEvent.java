package de.bht.planningpoker.event;

import de.bht.planningpoker.model.Discussion;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
@EqualsAndHashCode(callSuper = true)
public class DiscussionEndedEvent extends ApplicationEvent {
    Discussion discussion;

    public DiscussionEndedEvent(Discussion discussion) {
        super(discussion);
        this.discussion = discussion;
    }
}
