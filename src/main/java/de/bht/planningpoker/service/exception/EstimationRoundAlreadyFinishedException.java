package de.bht.planningpoker.service.exception;

public class EstimationRoundAlreadyFinishedException extends ServiceException {

    public EstimationRoundAlreadyFinishedException(String sessionId) {
        super(ServiceErrorCode.OPERATION_ERROR, "Estimation round in session " + sessionId + " is already finished");
    }

}
