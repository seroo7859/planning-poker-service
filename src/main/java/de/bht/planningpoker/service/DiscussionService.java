package de.bht.planningpoker.service;

import de.bht.planningpoker.model.Discussion;
import de.bht.planningpoker.model.DiscussionPost;
import de.bht.planningpoker.service.exception.ServiceException;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
public interface DiscussionService {

    @PreAuthorize("hasAnyRole('MODERATOR', 'PARTICIPANT', 'SPECTATOR')")
    @Transactional(readOnly = true)
    Discussion getDiscussion(@UUID(message = "The session id must be a UUID") String sessionId) throws ServiceException;

    @PreAuthorize("hasRole('MODERATOR')")
    @Transactional(rollbackFor = Exception.class)
    Discussion startDiscussion(@UUID(message = "The session id must be a UUID") String sessionId, String topic) throws ServiceException;

    @PreAuthorize("hasRole('MODERATOR')")
    @Transactional(rollbackFor = Exception.class)
    Discussion endDiscussion(@UUID(message = "The session id must be a UUID") String sessionId) throws ServiceException;

    @PreAuthorize("hasAnyRole('MODERATOR', 'PARTICIPANT')")
    @Transactional(rollbackFor = Exception.class)
    DiscussionPost createDiscussionPost(@UUID(message = "The session id must be a UUID") String sessionId, @Valid DiscussionPost discussionPost) throws ServiceException;

}
