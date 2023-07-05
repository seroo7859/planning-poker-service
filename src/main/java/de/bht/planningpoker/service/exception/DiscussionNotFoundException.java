package de.bht.planningpoker.service.exception;

public class DiscussionNotFoundException extends ServiceException {

    public DiscussionNotFoundException(String sessionId) {
        super(ServiceErrorCode.ENTITY_NOT_FOUND, "Could not find discussion with the session id " + sessionId);
    }

}
