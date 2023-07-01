package de.bht.planningpoker.event;

import de.bht.planningpoker.model.Estimation;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
@EqualsAndHashCode(callSuper = true)
public class EstimationGivenEvent extends ApplicationEvent {
    Estimation estimation;

    public EstimationGivenEvent(Estimation estimation) {
        super(estimation);
        this.estimation = estimation;
    }
}
