package de.bht.planningpoker.event;

import de.bht.planningpoker.model.EstimationRound;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
@EqualsAndHashCode(callSuper = true)
public class EstimationRoundFinishedEvent extends ApplicationEvent {
    EstimationRound estimationRound;

    public EstimationRoundFinishedEvent(EstimationRound estimationRound) {
        super(estimationRound);
        this.estimationRound = estimationRound;
    }
}
