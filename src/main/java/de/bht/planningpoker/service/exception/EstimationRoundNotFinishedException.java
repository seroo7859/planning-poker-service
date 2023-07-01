package de.bht.planningpoker.service.exception;

public class EstimationRoundNotFinishedException extends ServiceException {

    public EstimationRoundNotFinishedException(String sessionId) {
        super(ServiceErrorCode.OPERATION_ERROR, "Estimation round in session " + sessionId + " is not finished");
    }

}
