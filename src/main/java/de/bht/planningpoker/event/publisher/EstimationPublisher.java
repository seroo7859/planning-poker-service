package de.bht.planningpoker.event.publisher;

import de.bht.planningpoker.event.EstimationGivenEvent;
import de.bht.planningpoker.event.EstimationRoundFinishedEvent;
import de.bht.planningpoker.event.EstimationRoundStartedEvent;
import de.bht.planningpoker.model.Estimation;
import de.bht.planningpoker.model.EstimationRound;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EstimationPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishEstimationGivenEvent(Estimation estimation) {
        eventPublisher.publishEvent(new EstimationGivenEvent(estimation));
    }

    public void publishEstimationRoundStartedEvent(EstimationRound estimationRound) {
        eventPublisher.publishEvent(new EstimationRoundStartedEvent(estimationRound));
    }

    public void publishEstimationRoundFinishedEvent(EstimationRound estimationRound) {
        eventPublisher.publishEvent(new EstimationRoundFinishedEvent(estimationRound));
    }

}
