package de.bht.planningpoker.event;

import de.bht.planningpoker.model.Backlog;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
@EqualsAndHashCode(callSuper = true)
public class BacklogRenamedEvent extends ApplicationEvent {
    Backlog backlog;

    public BacklogRenamedEvent(Backlog backlog) {
        super(backlog);
        this.backlog = backlog;
    }
}
