package de.bht.planningpoker.event.listener;

import de.bht.planningpoker.controller.dto.TeamRenameDto;
import de.bht.planningpoker.event.*;
import de.bht.planningpoker.model.Session;
import de.bht.planningpoker.model.Team;
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
public class TeamListener {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTeamRenamed(TeamRenamedEvent event) {
        Team team = event.getTeam();
        Session session = team.getSession();

        log.info("Team renamed to '{}' on session {}", team.getName(), session.getPublicId());
        simpMessagingTemplate.convertAndSend(String.format("/session/%s/team/renamed", session.getPublicId()), new TeamRenameDto(team.getName()));
    }

}
