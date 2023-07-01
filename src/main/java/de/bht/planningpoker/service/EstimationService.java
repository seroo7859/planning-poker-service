package de.bht.planningpoker.service;

import de.bht.planningpoker.model.Estimation;
import de.bht.planningpoker.model.EstimationRound;
import de.bht.planningpoker.model.EstimationSummary;
import de.bht.planningpoker.service.exception.ServiceException;
import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
public interface EstimationService {

    @PreAuthorize("hasRole('MODERATOR')")
    @Transactional(rollbackFor = Exception.class)
    EstimationRound startEstimationRound(@UUID(message = "The session id must be a UUID") String sessionId, String backlogItemNumber) throws ServiceException;

    @PreAuthorize("hasRole('MODERATOR')")
    @Transactional(rollbackFor = Exception.class)
    EstimationRound nextEstimationRound(@UUID(message = "The session id must be a UUID") String sessionId) throws ServiceException;

    @PreAuthorize("hasRole('MODERATOR')")
    @Transactional(rollbackFor = Exception.class)
    EstimationRound finishEstimationRound(@UUID(message = "The session id must be a UUID") String sessionId) throws ServiceException;

    @PreAuthorize("hasAnyRole('MODERATOR', 'PARTICIPANT', 'SPECTATOR')")
    @Transactional(readOnly = true)
    EstimationRound getCurrentEstimationRound(@UUID(message = "The session id must be a UUID") String sessionId) throws ServiceException;

    @PreAuthorize("hasAnyRole('MODERATOR', 'PARTICIPANT')")
    @Transactional(rollbackFor = Exception.class)
    Estimation giveAnEstimation(@UUID(message = "The session id must be a UUID") String sessionId, String estimationValue) throws ServiceException;

}
