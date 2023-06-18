package de.bht.planningpoker.service.exception;

public class BacklogImportFailedException extends ServiceException {

    public BacklogImportFailedException(String sessionId) {
        super(ServiceErrorCode.SAVE_ENTITY_FAILED, "Could not import backlog on session " + sessionId);
    }

}
