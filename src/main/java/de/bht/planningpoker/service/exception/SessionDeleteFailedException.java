package de.bht.planningpoker.service.exception;

public class SessionDeleteFailedException extends ServiceException {

    public SessionDeleteFailedException(String publicId) {
        super(ServiceErrorCode.DELETE_ENTITY_FAILED, "Could not delete session with the public id " + publicId);
    }

}
