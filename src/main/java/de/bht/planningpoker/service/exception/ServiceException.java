package de.bht.planningpoker.service.exception;

public class ServiceException extends Exception {

    private final ServiceErrorCode errorCode;

    public ServiceException(ServiceErrorCode errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public ServiceException(ServiceErrorCode errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
    }

    public ServiceException(ServiceErrorCode errorCode, Throwable cause) {
        this(errorCode, cause.getMessage(), cause);
    }

    public ServiceErrorCode getErrorCode() {
        return errorCode;
    }

}
