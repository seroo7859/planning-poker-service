package de.bht.planningpoker.event;

import de.bht.planningpoker.model.BacklogItem;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
@EqualsAndHashCode(callSuper = true)
public class BacklogItemRemovedEvent extends ApplicationEvent {
    BacklogItem backlogItem;

    public BacklogItemRemovedEvent(BacklogItem backlogItem) {
        super(backlogItem);
        this.backlogItem = backlogItem;
    }
}
