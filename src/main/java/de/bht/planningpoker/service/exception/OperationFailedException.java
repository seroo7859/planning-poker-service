package de.bht.planningpoker.service.exception;

public class OperationFailedException extends ServiceException {
    public OperationFailedException(String message) {
        super(ServiceErrorCode.OPERATION_ERROR, message);
    }

    public OperationFailedException(String message, Throwable cause) {
        super(ServiceErrorCode.OPERATION_ERROR, message, cause);
    }
}
