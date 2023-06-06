package de.bht.planningpoker.service.exception;

public class SessionReadFailedException extends ServiceException {

    public SessionReadFailedException(String publicId) {
        super(ServiceErrorCode.READ_ERROR, "Could not read session with the public id " + publicId);
    }

}
