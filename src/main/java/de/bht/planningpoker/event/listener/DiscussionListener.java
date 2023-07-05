package de.bht.planningpoker.event.listener;

import de.bht.planningpoker.controller.mapper.DiscussionMapper;
import de.bht.planningpoker.event.DiscussionEndedEvent;
import de.bht.planningpoker.event.DiscussionPostCreatedEvent;
import de.bht.planningpoker.event.DiscussionStartedEvent;
import de.bht.planningpoker.model.*;
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
public class DiscussionListener {

    private final DiscussionMapper mapper;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleDiscussionPostCreated(DiscussionPostCreatedEvent event) {
        DiscussionPost discussionPost = event.getDiscussionPost();
        Session session = discussionPost.getDiscussion().getSession();

        log.info("Discussion post created from user {} in session {}", discussionPost.getAuthor().getUsername(), session.getPublicId());
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/discussion/post-created", session.getPublicId()), mapper.mapToDto(discussionPost));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, condition = "#event.discussion.active")
    public void handleDiscussionStarted(DiscussionStartedEvent event) {
        Discussion discussion = event.getDiscussion();
        Session session = discussion.getSession();

        log.info("Discussion started in session {}", session.getPublicId());
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/discussion/started", session.getPublicId()), mapper.mapToDto(discussion));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, condition = "!#event.discussion.active")
    public void handleDiscussionEnded(DiscussionEndedEvent event) {
        Discussion discussion = event.getDiscussion();
        Session session = discussion.getSession();

        log.info("Discussion ended in session {}", session.getPublicId());
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/discussion/ended", session.getPublicId()), mapper.mapToDto(discussion));
    }

}
