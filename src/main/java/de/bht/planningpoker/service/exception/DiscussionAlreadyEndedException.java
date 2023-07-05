package de.bht.planningpoker.service.exception;

public class DiscussionAlreadyEndedException extends ServiceException {

    public DiscussionAlreadyEndedException(String sessionId) {
        super(ServiceErrorCode.OPERATION_ERROR, "Discussion in session " + sessionId + " is already ended");
    }

}
