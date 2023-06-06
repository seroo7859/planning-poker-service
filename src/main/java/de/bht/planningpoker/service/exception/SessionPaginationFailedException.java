package de.bht.planningpoker.service.exception;

public class SessionPaginationFailedException extends ServiceException {

    public SessionPaginationFailedException() {
        super(ServiceErrorCode.PAGINATION_FAILED, "Could not paginate session");
    }

}
