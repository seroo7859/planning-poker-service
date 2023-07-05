package de.bht.planningpoker.service;

import de.bht.planningpoker.event.publisher.DiscussionPublisher;
import de.bht.planningpoker.model.Discussion;
import de.bht.planningpoker.model.DiscussionPost;
import de.bht.planningpoker.model.User;
import de.bht.planningpoker.repository.DiscussionPostRepository;
import de.bht.planningpoker.repository.DiscussionRepository;
import de.bht.planningpoker.service.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscussionServiceImpl implements DiscussionService {

    private final DiscussionRepository discussionRepository;
    private final DiscussionPostRepository discussionPostRepository;
    private final DiscussionPublisher discussionPublisher;
    private final UserService userService;

    @Override
    public Discussion getDiscussion(String sessionId) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId())) {
                throw new OperationNotAllowedException("Get discussion on session " + sessionId + " is not allowed");
            }

            Optional<Discussion> discussionFound = discussionRepository.findBySessionPublicId(sessionId);
            return discussionFound.orElseThrow(() -> new DiscussionNotFoundException(sessionId));
        } catch (DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.READ_ERROR, e);
        }
    }

    @Override
    public Discussion startDiscussion(String sessionId, String topic) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank() || Objects.isNull(topic) || topic.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId()) || !currentUser.isModerator()) {
                throw new OperationNotAllowedException("Start discussion in session " + sessionId + " is not allowed");
            }

            Discussion discussion = discussionRepository.findBySessionPublicId(sessionId)
                    .orElseThrow(() -> new DiscussionNotFoundException(sessionId));

            if (discussion.isStarted()) {
                throw new DiscussionAlreadyStartedException(sessionId);
            }

            discussion.setTopic(topic);
            discussion.start();
            discussionRepository.save(discussion);
            discussionPublisher.publishDiscussionStartedEvent(discussion);
            return discussion;
        } catch (DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.UPDATE_ERROR, e);
        }
    }

    @Override
    public Discussion endDiscussion(String sessionId) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank()) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId()) || !currentUser.isModerator()) {
                throw new OperationNotAllowedException("End discussion in session " + sessionId + " is not allowed");
            }

            Discussion discussion = discussionRepository.findBySessionPublicId(sessionId)
                    .orElseThrow(() -> new DiscussionNotFoundException(sessionId));

            if (discussion.isEnded()) {
                throw new DiscussionAlreadyEndedException(sessionId);
            }

            discussion.end();
            discussionRepository.save(discussion);
            discussionPublisher.publishDiscussionEndedEvent(discussion);
            return discussion;
        } catch (DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.UPDATE_ERROR, e);
        }
    }

    @Override
    public DiscussionPost createDiscussionPost(String sessionId, DiscussionPost discussionPost) throws ServiceException {
        if (Objects.isNull(sessionId) || sessionId.isBlank() || Objects.isNull(discussionPost)) {
            throw new ParameterNullException();
        }
        try {
            User currentUser = userService.getCurrentUser();
            if (!sessionId.equals(currentUser.getSessionId())) {
                throw new OperationNotAllowedException("Create discussion post in session " + sessionId + " is not allowed");
            }

            Discussion discussion = discussionRepository.findBySessionPublicId(sessionId)
                    .orElseThrow(() -> new DiscussionNotFoundException(sessionId));

            if (discussion.isEnded()) {
                throw new DiscussionAlreadyEndedException(sessionId);
            }

            discussion.addPost(discussionPost);
            discussionPostRepository.save(discussionPost);
            discussionRepository.save(discussion);
            discussionPublisher.publishDiscussionPostCreatedEvent(discussionPost);
            return discussionPost;
        } catch (DataAccessException e) {
            throw new ServiceException(ServiceErrorCode.CREATE_ERROR, e);
        }
    }

}
