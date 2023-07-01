package de.bht.planningpoker.service;

import de.bht.planningpoker.event.publisher.UserPublisher;
import de.bht.planningpoker.model.Role;
import de.bht.planningpoker.model.Session;
import de.bht.planningpoker.model.Team;
import de.bht.planningpoker.model.User;
import de.bht.planningpoker.repository.SessionRepository;
import de.bht.planningpoker.service.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository repository;
    private final UserService userService;
    private final UserPublisher userPublisher;

    @Override
    public Page<Session> paginateSession(Pageable pageable) throws ServiceException {
        if (Objects.isNull(pageable)) {
            throw new ParameterNullException();
        }
        try {
            Optional<Page<Session>> paginatedSession = Optional.of(repository.findAllSessions(pageable));
            return paginatedSession.orElseThrow(SessionPaginationFailedException::new);
        } catch(DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.READ_ERROR, e);
        }
    }

    @Override
    public Session getSession(String publicId) throws ServiceException {
        if (Objects.isNull(publicId) || publicId.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            if (!repository.existsByPublicId(publicId)) {
                throw new SessionNotFoundException(publicId);
            }

            // Get session details only for joined team members
            User currentUser = userService.getCurrentUser();
            Optional<Session> session = repository.findByPublicIdAndUser(publicId, currentUser);
            return session.orElseThrow(() -> new SessionReadFailedException(publicId));
        } catch(DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.READ_ERROR, e);
        }
    }

    @Override
    public Session createSession(Session session) throws ServiceException {
        if (Objects.isNull(session)) {
            throw new ParameterNullException();
        }
        try {
            Optional<Session> createdSession = Optional.of(repository.save(session));
            createdSession.ifPresent(newSession -> {
                User user = newSession.getTeam()
                        .getMembers()
                        .get(0);
                if (Objects.nonNull(user) && user.getRole().equals(Role.MODERATOR)) {
                    repository.updateCreatedBy(newSession.getId(), user);
                    userPublisher.publishUserJoinedEvent(user);
                }
            });
            return createdSession.orElseThrow(() -> new SessionCreateFailedException(session));
        } catch(DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.CREATE_ERROR, e);
        }
    }

    @Override
    public void deleteSession(String publicId) throws ServiceException {
        if (Objects.isNull(publicId) || publicId.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            if (!repository.existsByPublicId(publicId)) {
                throw new SessionNotFoundException(publicId);
            }

            // Cascading delete the session from user which created
            User currentUser = userService.getCurrentUser();
            int deletedSessions = repository.deleteByPublicIdAndCreatedBy(publicId, currentUser);
            if (deletedSessions == 0) {
                throw new SessionDeleteFailedException(publicId);
            }
        } catch(DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.DELETE_ERROR, e);
        }
    }

    @Override
    public Session joinSession(String publicId, User user) throws ServiceException {
        if (Objects.isNull(publicId) || publicId.isBlank() || Objects.isNull(user)) {
            throw new ParameterNullException();
        }
        try {
            Optional<Session> sessionFound = repository.findOneByPublicId(publicId);
            Session session = sessionFound.orElseThrow(() -> new SessionNotFoundException(publicId));

            Team team = session.getTeam();
            User teamMember = team.getMember(user.getUsername());

            if (Objects.nonNull(teamMember)) {
                if (teamMember.isActive()) {
                    throw new TeamMemberAlreadyExistsException(teamMember);
                } else {
                    if (!teamMember.getRole().equals(Role.MODERATOR)) {
                        teamMember.setRole(user.getRole());
                    }
                    return session;
                }
            } else {
                if (team.getSize() >= Team.MAX_TEAM_SIZE) {
                    throw new MaxTeamSizeReachedException(Team.MAX_TEAM_SIZE);
                }
                team.addMember(user);
                userPublisher.publishUserJoinedEvent(user);
                return session;
            }
        } catch(DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.UPDATE_ERROR, e);
        }
    }

    @Override
    public Session leaveSession(String publicId, User user) throws ServiceException {
        if (Objects.isNull(publicId) || publicId.isBlank() || Objects.isNull(user)) {
            throw new ParameterNullException();
        }
        try {
            Optional<Session> sessionFound = repository.findOneByPublicId(publicId);
            Session session = sessionFound.orElseThrow(() -> new SessionNotFoundException(publicId));

            Team team = session.getTeam();
            User teamMember = team.getMember(user.getUsername());

            if (Objects.nonNull(teamMember)) {
                if (teamMember.getRole().equals(Role.MODERATOR)) {
                    return session;
                }

                team.removeMember(teamMember.clone());
                userPublisher.publishUserLeavedEvent(teamMember);
                return session;
            } else {
                throw new TeamMemberNotFoundException(user);
            }
        } catch (DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.UPDATE_ERROR, e);
        }
    }
}
