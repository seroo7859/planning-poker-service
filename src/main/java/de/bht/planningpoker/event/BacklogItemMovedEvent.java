package de.bht.planningpoker.event;

import de.bht.planningpoker.model.BacklogItem;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
@EqualsAndHashCode(callSuper = true)
public class BacklogItemMovedEvent extends ApplicationEvent {
    BacklogItem backlogItem;

    public BacklogItemMovedEvent(BacklogItem backlogItem) {
        super(backlogItem);
        this.backlogItem = backlogItem;
    }
}
