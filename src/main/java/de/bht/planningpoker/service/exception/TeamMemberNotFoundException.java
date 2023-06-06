package de.bht.planningpoker.service.exception;

import de.bht.planningpoker.model.User;

public class TeamMemberNotFoundException extends ServiceException {

    public TeamMemberNotFoundException(User user) {
        this(user.getUsername());
    }

    public TeamMemberNotFoundException(String username) {
        super(ServiceErrorCode.ENTITY_NOT_FOUND, "Could not find team member with the username " + username);
    }

}
