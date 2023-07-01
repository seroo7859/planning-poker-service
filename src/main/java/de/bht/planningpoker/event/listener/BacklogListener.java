package de.bht.planningpoker.event.listener;

import de.bht.planningpoker.controller.dto.BacklogItemMoveResultDto;
import de.bht.planningpoker.controller.dto.BacklogRenameDto;
import de.bht.planningpoker.controller.mapper.BacklogMapper;
import de.bht.planningpoker.event.*;
import de.bht.planningpoker.model.Backlog;
import de.bht.planningpoker.model.BacklogItem;
import de.bht.planningpoker.model.Session;
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
public class BacklogListener {

    private final BacklogMapper mapper;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBacklogImported(BacklogImportedEvent event) {
        Backlog backlog = event.getBacklog();
        Session session = backlog.getSession();

        log.info("Backlog '{}' imported on session {}", backlog.getName(), session.getPublicId());
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/backlog/imported", session.getPublicId()), mapper.mapToDto(backlog));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBacklogRenamed(BacklogRenamedEvent event) {
        Backlog backlog = event.getBacklog();
        Session session = backlog.getSession();

        log.info("Backlog renamed to '{}' on session {}", backlog.getName(), session.getPublicId());
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/backlog/renamed", session.getPublicId()), new BacklogRenameDto(backlog.getName()));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBacklogCleared(BacklogClearedEvent event) {
        Backlog backlog = event.getBacklog();
        Session session = backlog.getSession();

        log.info("Backlog '{}' cleared on session {}", backlog.getName(), session.getPublicId());
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/backlog/cleared", session.getPublicId()), mapper.mapToDto(backlog));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBacklogItemAdded(BacklogItemAddedEvent event) {
        BacklogItem backlogItem = event.getBacklogItem();
        Session session = backlogItem.getBacklog().getSession();

        log.info("Backlog item {} added on session {}", backlogItem.getNumber(), session.getPublicId());
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/backlog/item-added", session.getPublicId()), mapper.mapToDto(backlogItem));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBacklogItemRemoved(BacklogItemRemovedEvent event) {
        BacklogItem backlogItem = event.getBacklogItem();
        Session session = backlogItem.getBacklog().getSession();

        log.info("Backlog item {} removed on session {}", backlogItem.getNumber(), session.getPublicId());
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/backlog/item-removed", session.getPublicId()), mapper.mapToDto(backlogItem));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBacklogItemUpdated(BacklogItemUpdatedEvent event) {
        BacklogItem backlogItem = event.getBacklogItem();
        Session session = backlogItem.getBacklog().getSession();

        log.info("Backlog item {} updated on session {}", backlogItem.getNumber(), session.getPublicId());
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/backlog/item-updated", session.getPublicId()), mapper.mapToDto(backlogItem));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBacklogItemMoved(BacklogItemMovedEvent event) {
        BacklogItem backlogItem = event.getBacklogItem();
        Backlog backlog = backlogItem.getBacklog();
        Session session = backlog.getSession();
        int backlogItemIndex = backlog.getItemIndex(backlogItem.getNumber());

        log.info("Backlog item {} moved to new index {} on session {}", backlogItem.getNumber(), backlogItemIndex, session.getPublicId());

        BacklogItemMoveResultDto dto = new BacklogItemMoveResultDto(mapper.mapToDto(backlog), mapper.mapToDto(backlogItem), backlogItemIndex);
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/backlog/item-moved", session.getPublicId()), dto);
    }

}
