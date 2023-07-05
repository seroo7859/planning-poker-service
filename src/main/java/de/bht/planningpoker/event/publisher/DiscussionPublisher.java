package de.bht.planningpoker.event.publisher;

import de.bht.planningpoker.event.*;
import de.bht.planningpoker.model.Discussion;
import de.bht.planningpoker.model.DiscussionPost;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscussionPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishDiscussionPostCreatedEvent(DiscussionPost discussionPost) {
        eventPublisher.publishEvent(new DiscussionPostCreatedEvent(discussionPost));
    }

    public void publishDiscussionStartedEvent(Discussion discussion) {
        eventPublisher.publishEvent(new DiscussionStartedEvent(discussion));
    }

    public void publishDiscussionEndedEvent(Discussion discussion) {
        eventPublisher.publishEvent(new DiscussionEndedEvent(discussion));
    }

}
