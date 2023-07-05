package de.bht.planningpoker.event;

import de.bht.planningpoker.model.DiscussionPost;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
@EqualsAndHashCode(callSuper = true)
public class DiscussionPostCreatedEvent extends ApplicationEvent {
    DiscussionPost discussionPost;

    public DiscussionPostCreatedEvent(DiscussionPost discussionPost) {
        super(discussionPost);
        this.discussionPost = discussionPost;
    }
}
