package de.bht.planningpoker.event;

import de.bht.planningpoker.model.Team;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
@EqualsAndHashCode(callSuper = true)
public class TeamRenamedEvent extends ApplicationEvent {
    Team team;

    public TeamRenamedEvent(Team team) {
        super(team);
        this.team = team;
    }
}
