package de.bht.planningpoker.service.exception;

public class EstimationRoundNotFoundException extends ServiceException {

    public EstimationRoundNotFoundException() {
        super(ServiceErrorCode.ENTITY_NOT_FOUND, "Could not find estimation round");
    }

}
