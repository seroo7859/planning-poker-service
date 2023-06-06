package de.bht.planningpoker.service.exception;

public class MaxTeamSizeReachedException extends ServiceException {

    public MaxTeamSizeReachedException(int maxTeamSize) {
        super(ServiceErrorCode.SAVE_ENTITY_FAILED, "The maximum team size of " + maxTeamSize + " members reached");
    }

}
