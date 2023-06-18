package de.bht.planningpoker.event;

import de.bht.planningpoker.model.Backlog;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
@EqualsAndHashCode(callSuper = true)
public class BacklogClearedEvent extends ApplicationEvent {
    Backlog backlog;

    public BacklogClearedEvent(Backlog backlog) {
        super(backlog);
        this.backlog = backlog;
    }
}
