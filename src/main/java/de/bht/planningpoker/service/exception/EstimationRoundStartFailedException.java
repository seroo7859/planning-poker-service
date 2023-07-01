package de.bht.planningpoker.service.exception;

public class EstimationRoundStartFailedException extends ServiceException {

    public EstimationRoundStartFailedException(String sessionId) {
        super(ServiceErrorCode.OPERATION_ERROR, "Could not start estimation round in session " + sessionId);
    }

}
