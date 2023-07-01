package de.bht.planningpoker.service;

import de.bht.planningpoker.model.Team;
import de.bht.planningpoker.service.exception.ServiceException;
import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TeamService {

    @PreAuthorize("hasAnyRole('MODERATOR', 'PARTICIPANT', 'SPECTATOR')")
    @Transactional(readOnly = true)
    Team getTeam(String sessionId) throws ServiceException;

    @PreAuthorize("hasRole('MODERATOR')")
    @Transactional(rollbackFor = Exception.class)
    Team renameTeam(@UUID(message = "The session id must be a UUID") String sessionId, String newName) throws ServiceException;

}
