package de.bht.planningpoker.service.exception;

public class BacklogExportFailedException extends ServiceException {

    public BacklogExportFailedException(String sessionId) {
        super(ServiceErrorCode.OPERATION_ERROR, "Could not export backlog on session " + sessionId);
    }

}
