package de.bht.planningpoker.service;

import de.bht.planningpoker.event.publisher.TeamPublisher;
import de.bht.planningpoker.model.Team;
import de.bht.planningpoker.model.User;
import de.bht.planningpoker.repository.TeamRepository;
import de.bht.planningpoker.service.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository repository;
    private final TeamPublisher publisher;
    private final UserService userService;

    @Override
    public Team getTeam(String sessionId) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId())) {
                throw new OperationNotAllowedException("Get team on session " + sessionId + " is not allowed");
            }

            Optional<Team> teamFound = repository.findBySessionPublicId(sessionId);
            return teamFound.orElseThrow(() -> new TeamNotFoundException(sessionId));
        } catch (DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.READ_ERROR, e);
        }
    }

    @Override
    public Team renameTeam(String sessionId, String newName) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank() || Objects.isNull(newName) || newName.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId()) || !currentUser.isModerator()) {
                throw new OperationNotAllowedException("Rename team on session " + sessionId + " is not allowed");
            }

            Optional<Team> teamFound = repository.findBySessionPublicId(sessionId);
            teamFound.ifPresent(team -> {
                if (!newName.equals(team.getName())) {
                    team.setName(newName);
                    repository.save(team);
                    publisher.publishTeamRenamedEvent(team);
                }
            });
            return teamFound.orElseThrow(() -> new TeamNotFoundException(sessionId));
        } catch(DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.UPDATE_ERROR, e);
        }
    }
}
