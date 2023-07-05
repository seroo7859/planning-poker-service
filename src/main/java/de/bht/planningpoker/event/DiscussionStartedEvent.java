package de.bht.planningpoker.event;

import de.bht.planningpoker.model.Discussion;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
@EqualsAndHashCode(callSuper = true)
public class DiscussionStartedEvent extends ApplicationEvent {
    Discussion discussion;

    public DiscussionStartedEvent(Discussion discussion) {
        super(discussion);
        this.discussion = discussion;
    }
}
