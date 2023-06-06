package de.bht.planningpoker.service.exception;

public class ParameterNullException extends ServiceException {

    public ParameterNullException() {
        super(ServiceErrorCode.NULL_VALUE, "Value cannot be null");
    }

}
