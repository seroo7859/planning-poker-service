package de.bht.planningpoker.event.publisher;

import de.bht.planningpoker.event.TeamRenamedEvent;
import de.bht.planningpoker.model.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeamPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishTeamRenamedEvent(Team team) {
        eventPublisher.publishEvent(new TeamRenamedEvent(team));
    }

}
