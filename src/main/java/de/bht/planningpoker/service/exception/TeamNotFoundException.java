package de.bht.planningpoker.service.exception;

public class TeamNotFoundException extends ServiceException {

    public TeamNotFoundException(String sessionId) {
        super(ServiceErrorCode.ENTITY_NOT_FOUND, "Could not find team with the session id " + sessionId);
    }

}
