package de.bht.planningpoker.service.exception;

public class DiscussionAlreadyStartedException extends ServiceException {

    public DiscussionAlreadyStartedException(String sessionId) {
        super(ServiceErrorCode.OPERATION_ERROR, "Discussion in session " + sessionId + " is already started");
    }

}
