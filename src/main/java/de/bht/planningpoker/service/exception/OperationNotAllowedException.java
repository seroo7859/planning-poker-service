package de.bht.planningpoker.service.exception;

public class OperationNotAllowedException extends ServiceException {
    public OperationNotAllowedException(String message) {
        super(ServiceErrorCode.OPERATION_ERROR, message);
    }
}
