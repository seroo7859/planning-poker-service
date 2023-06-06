package de.bht.planningpoker.service.exception;

public class SessionNotFoundException extends ServiceException {

    public SessionNotFoundException(String publicId) {
        super(ServiceErrorCode.ENTITY_NOT_FOUND, "Could not find session with the public id " + publicId);
    }

}
