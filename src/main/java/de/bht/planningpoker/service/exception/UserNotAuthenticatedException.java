package de.bht.planningpoker.service.exception;

public class UserNotAuthenticatedException extends ServiceException {

    public UserNotAuthenticatedException() {
        super(ServiceErrorCode.UNAUTHORIZED, "User is not authenticated");
    }

}
