package de.bht.planningpoker.service;

import de.bht.planningpoker.model.Session;
import de.bht.planningpoker.model.User;
import de.bht.planningpoker.service.exception.ServiceException;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
public interface SessionService {

    @Transactional(readOnly = true)
    Page<Session> paginateSession(Pageable pageable) throws ServiceException;

    @PreAuthorize("hasAnyRole('MODERATOR', 'PARTICIPANT', 'SPECTATOR')")
    @Transactional(readOnly = true)
    Session getSession(@UUID(message = "The session id must be a UUID") String publicId) throws ServiceException;

    @Transactional(rollbackFor = Exception.class)
    Session createSession(@Valid Session session) throws ServiceException;

    @PreAuthorize("hasRole('MODERATOR')")
    @Transactional(rollbackFor = Exception.class)
    void deleteSession(@UUID(message = "The session id must be a UUID") String publicId) throws ServiceException;

    @Transactional(rollbackFor = Exception.class)
    Session joinSession(@UUID(message = "The session id must be a UUID") String publicId, @Valid User user) throws ServiceException;

    @PreAuthorize("hasAnyRole('MODERATOR', 'PARTICIPANT', 'SPECTATOR')")
    @Transactional(rollbackFor = Exception.class)
    Session leaveSession(@UUID(message = "The session id must be a UUID") String publicId, @Valid User user) throws ServiceException;

}
