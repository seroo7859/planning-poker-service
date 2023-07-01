package de.bht.planningpoker.event.listener;

import de.bht.planningpoker.controller.mapper.EstimationMapper;
import de.bht.planningpoker.event.EstimationGivenEvent;
import de.bht.planningpoker.event.EstimationRoundFinishedEvent;
import de.bht.planningpoker.event.EstimationRoundStartedEvent;
import de.bht.planningpoker.model.Estimation;
import de.bht.planningpoker.model.EstimationRound;
import de.bht.planningpoker.model.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class EstimationListener {

    private final EstimationMapper mapper;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEstimationGiven(EstimationGivenEvent event) {
        Estimation estimation = event.getEstimation();
        EstimationRound estimationRound = estimation.getEstimationRound();
        Session session = estimationRound.getSession();

        log.info("User {} give an estimation {} for the backlog item {} in session {}", estimation.getCreatedBy().getUsername(), estimation.getEstimationValue(), estimationRound.getBacklogItem().getNumber(), session.getPublicId());
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/estimation/given", session.getPublicId()), mapper.mapToDto(estimation));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEstimationRoundStarted(EstimationRoundStartedEvent event) {
        EstimationRound estimationRound = event.getEstimationRound();
        Session session = estimationRound.getSession();

        log.info("Estimation round for the backlog item {} started in session {}", estimationRound.getBacklogItem().getNumber(), session.getPublicId());
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/estimation/round/started", session.getPublicId()), mapper.mapToDto(estimationRound));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEstimationRoundFinished(EstimationRoundFinishedEvent event) {
        EstimationRound estimationRound = event.getEstimationRound();
        Session session = estimationRound.getSession();

        log.info("Estimation round for the backlog item {} finished in session {}", estimationRound.getBacklogItem().getNumber(), session.getPublicId());
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/estimation/round/finished", session.getPublicId()), mapper.mapToDto(estimationRound));
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/estimation/round/summary", session.getPublicId()), mapper.mapToDto(estimationRound.getSummary()));
    }

}
