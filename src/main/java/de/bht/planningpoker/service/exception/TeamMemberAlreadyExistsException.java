package de.bht.planningpoker.service.exception;

import de.bht.planningpoker.model.User;

public class TeamMemberAlreadyExistsException extends ServiceException  {

    public TeamMemberAlreadyExistsException(User teamMember) {
        super(ServiceErrorCode.ENTITY_EXISTS, "Team member with the username \"" + teamMember.getUsername() + "\" already exists");
    }

}
