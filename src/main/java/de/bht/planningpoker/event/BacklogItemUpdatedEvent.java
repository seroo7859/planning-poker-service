package de.bht.planningpoker.event;

import de.bht.planningpoker.model.BacklogItem;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
@EqualsAndHashCode(callSuper = true)
public class BacklogItemUpdatedEvent extends ApplicationEvent {
    BacklogItem backlogItem;

    public BacklogItemUpdatedEvent(BacklogItem backlogItem) {
        super(backlogItem);
        this.backlogItem = backlogItem;
    }
}
