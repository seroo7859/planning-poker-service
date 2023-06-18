package de.bht.planningpoker.service.exception;

public class BacklogNotFoundException extends ServiceException {

    public BacklogNotFoundException(String sessionId) {
        super(ServiceErrorCode.ENTITY_NOT_FOUND, "Could not find backlog with the session id " + sessionId);
    }

}
