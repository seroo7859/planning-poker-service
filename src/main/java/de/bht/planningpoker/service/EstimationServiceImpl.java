package de.bht.planningpoker.service;

import de.bht.planningpoker.event.publisher.EstimationPublisher;
import de.bht.planningpoker.model.*;
import de.bht.planningpoker.repository.EstimationRepository;
import de.bht.planningpoker.repository.EstimationRoundRepository;
import de.bht.planningpoker.service.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstimationServiceImpl implements EstimationService {

    private final EstimationRoundRepository estimationRoundRepository;
    private final EstimationRepository estimationRepository;
    private final EstimationPublisher estimationPublisher;

    private final BacklogService backlogService;
    private final SessionService sessionService;
    private final UserService userService;

    @Override
    public EstimationRound startEstimationRound(String sessionId, String backlogItemNumber) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank() || Objects.isNull(backlogItemNumber) || backlogItemNumber.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId())) {
                throw new OperationNotAllowedException("Start estimation round for the backlog item " + backlogItemNumber + " in session " + sessionId + " is not allowed");
            }

            Session session = sessionService.getSession(sessionId);

            Optional<EstimationRound> currentEstimationRound = session.getCurrentEstimationRound();
            if (currentEstimationRound.isPresent() && currentEstimationRound.get().isStarted()) {
                throw new EstimationRoundNotFinishedException(sessionId);
            }

            EstimationRound newEstimationRound = EstimationRound.builder()
                    .backlogItem(backlogService.getBacklogItem(sessionId, backlogItemNumber))
                    .session(session)
                    .estimations(List.of())
                    .build();

            newEstimationRound.start();
            estimationRoundRepository.save(newEstimationRound);
            estimationPublisher.publishEstimationRoundStartedEvent(newEstimationRound);
            return newEstimationRound;
        } catch (DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.CREATE_ERROR, e);
        }
    }

    @Override
    public EstimationRound nextEstimationRound(String sessionId) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId())) {
                throw new OperationNotAllowedException("Start the next estimation round in session " + sessionId + " is not allowed");
            }

            Session session = sessionService.getSession(sessionId);

            Optional<EstimationRound> currentEstimationRound = session.getCurrentEstimationRound();
            if (currentEstimationRound.isPresent() && currentEstimationRound.get().isStarted()) {
                throw new EstimationRoundNotFinishedException(sessionId);
            }

            Backlog backlog = session.getBacklog();
            List<BacklogItem> notEstimatedBacklogItems = backlog.getNotEstimatedItems();

            if (notEstimatedBacklogItems.isEmpty()) {
                throw new EstimationRoundStartFailedException(sessionId);
            }

            BacklogItem nextBacklogItem = notEstimatedBacklogItems.get(0);
            EstimationRound nextEstimationRound = EstimationRound.builder()
                    .backlogItem(nextBacklogItem)
                    .session(session)
                    .estimations(List.of())
                    .build();

            nextEstimationRound.start();
            estimationRoundRepository.save(nextEstimationRound);
            estimationPublisher.publishEstimationRoundStartedEvent(nextEstimationRound);
            return nextEstimationRound;
        } catch (DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.CREATE_ERROR, e);
        }
    }

    @Override
    public EstimationRound finishEstimationRound(String sessionId) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId())) {
                throw new OperationNotAllowedException("Finish the current estimation round in session " + sessionId + " is not allowed");
            }

            Session session = sessionService.getSession(sessionId);
            EstimationRound currentEstimationRound = session.getCurrentEstimationRound()
                    .orElseThrow(EstimationRoundNotFoundException::new);

            if (currentEstimationRound.isFinished()) {
                throw new EstimationRoundAlreadyFinishedException(sessionId);
            }

            currentEstimationRound.finish();
            estimationRoundRepository.save(currentEstimationRound);
            estimationPublisher.publishEstimationRoundFinishedEvent(currentEstimationRound);
            return currentEstimationRound;
        } catch (DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.UPDATE_ERROR, e);
        }
    }

    @Override
    public EstimationRound getCurrentEstimationRound(String sessionId) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId())) {
                throw new OperationNotAllowedException("Get current estimation round in session " + sessionId + " is not allowed");
            }

            Session session = sessionService.getSession(sessionId);
            return session.getCurrentEstimationRound()
                    .orElseThrow(EstimationRoundNotFoundException::new);
        } catch (DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.READ_ERROR, e);
        }
    }

    @Override
    public Estimation giveAnEstimation(String sessionId, String estimationValue) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank() || Objects.isNull(estimationValue) || estimationValue.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId())) {
                throw new OperationNotAllowedException("Give an estimation in session " + sessionId + " is not allowed");
            }

            Session session = sessionService.getSession(sessionId);
            EstimationRound currentEstimationRound = session.getCurrentEstimationRound()
                    .orElseThrow(EstimationRoundNotFoundException::new);

            if (currentEstimationRound.isFinished()) {
                throw new EstimationRoundAlreadyFinishedException(sessionId);
            }

            List<Estimation> estimationsFromEstimator = currentEstimationRound.getEstimationsFromEstimator(currentUser);
            if (estimationsFromEstimator.isEmpty()) {
                Estimation estimation = Estimation.builder()
                        .estimationValue(estimationValue)
                        .estimationRound(currentEstimationRound)
                        .createdBy(currentUser)
                        .build();

                currentEstimationRound.addEstimation(estimation);
                estimationRepository.save(estimation);
                estimationPublisher.publishEstimationGivenEvent(estimation);
                return estimation;
            }

            Estimation estimation = estimationsFromEstimator.get(0);
            estimation.setEstimationValue(estimationValue);
            estimationRepository.save(estimation);
            estimationPublisher.publishEstimationGivenEvent(estimation);
            return estimation;
        } catch (DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.CREATE_ERROR, e);
        }
    }

}
