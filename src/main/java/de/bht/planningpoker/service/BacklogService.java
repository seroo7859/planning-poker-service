package de.bht.planningpoker.service;

import de.bht.planningpoker.model.Backlog;
import de.bht.planningpoker.model.BacklogItem;
import de.bht.planningpoker.service.dto.BacklogExport;
import de.bht.planningpoker.service.exception.ServiceException;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
public interface BacklogService {

    @PreAuthorize("hasRole('MODERATOR')")
    @Transactional(rollbackFor = Exception.class)
    Backlog importBacklog(@UUID(message = "The session id must be a UUID") String sessionId, MultipartFile file) throws ServiceException;

    @PreAuthorize("hasRole('MODERATOR')")
    @Transactional(readOnly = true)
    BacklogExport exportBacklog(@UUID(message = "The session id must be a UUID") String sessionId) throws ServiceException;

    @PreAuthorize("hasAnyRole('MODERATOR', 'PARTICIPANT', 'SPECTATOR')")
    @Transactional(readOnly = true)
    Backlog getBacklog(String sessionId) throws ServiceException;

    @PreAuthorize("hasRole('MODERATOR')")
    @Transactional(rollbackFor = Exception.class)
    Backlog renameBacklog(@UUID(message = "The session id must be a UUID") String sessionId, String newName) throws ServiceException;

    @PreAuthorize("hasRole('MODERATOR')")
    @Transactional(rollbackFor = Exception.class)
    void clearBacklog(@UUID(message = "The session id must be a UUID") String sessionId) throws ServiceException;

    @PreAuthorize("hasAnyRole('MODERATOR', 'PARTICIPANT', 'SPECTATOR')")
    @Transactional(readOnly = true)
    BacklogItem getBacklogItem(@UUID(message = "The session id must be a UUID") String sessionId, String backlogItemNumber) throws ServiceException;

    @PreAuthorize("hasRole('MODERATOR')")
    @Transactional(rollbackFor = Exception.class)
    BacklogItem addBacklogItem(@UUID(message = "The session id must be a UUID") String sessionId, @Valid BacklogItem backlogItem) throws ServiceException;

    @PreAuthorize("hasRole('MODERATOR')")
    @Transactional(rollbackFor = Exception.class)
    void removeBacklogItem(@UUID(message = "The session id must be a UUID") String sessionId, String backlogItemNumber) throws ServiceException;

    @PreAuthorize("hasRole('MODERATOR')")
    @Transactional(rollbackFor = Exception.class)
    BacklogItem updateBacklogItem(@UUID(message = "The session id must be a UUID") String sessionId, String backlogItemNumber, @Valid BacklogItem newBacklogItem) throws ServiceException;

    @PreAuthorize("hasRole('MODERATOR')")
    @Transactional(rollbackFor = Exception.class)
    List<BacklogItem> moveBacklogItem(@UUID(message = "The session id must be a UUID") String sessionId, String backlogItemNumber, Integer newIndex) throws ServiceException;

}
