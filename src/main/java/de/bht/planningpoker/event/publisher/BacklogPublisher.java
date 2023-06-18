package de.bht.planningpoker.event.publisher;

import de.bht.planningpoker.event.*;
import de.bht.planningpoker.model.Backlog;
import de.bht.planningpoker.model.BacklogItem;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BacklogPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishBacklogImportedEvent(Backlog backlog) {
        eventPublisher.publishEvent(new BacklogImportedEvent(backlog));
    }

    public void publishBacklogRenamedEvent(Backlog backlog) {
        eventPublisher.publishEvent(new BacklogRenamedEvent(backlog));
    }

    public void publishBacklogClearedEvent(Backlog backlog) {
        eventPublisher.publishEvent(new BacklogClearedEvent(backlog));
    }

    public void publishBacklogItemAddedEvent(BacklogItem backlogItem) {
        eventPublisher.publishEvent(new BacklogItemAddedEvent(backlogItem));
    }

    public void publishBacklogItemRemovedEvent(BacklogItem backlogItem) {
        eventPublisher.publishEvent(new BacklogItemRemovedEvent(backlogItem));
    }

    public void publishBacklogItemUpdatedEvent(BacklogItem backlogItem) {
        eventPublisher.publishEvent(new BacklogItemUpdatedEvent(backlogItem));
    }

    public void publishBacklogItemMovedEvent(BacklogItem backlogItem) {
        eventPublisher.publishEvent(new BacklogItemMovedEvent(backlogItem));
    }

}
