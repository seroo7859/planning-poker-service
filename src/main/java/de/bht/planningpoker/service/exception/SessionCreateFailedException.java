package de.bht.planningpoker.service.exception;

import de.bht.planningpoker.model.Session;

public class SessionCreateFailedException extends ServiceException {

    public SessionCreateFailedException(Session session) {
        super(ServiceErrorCode.SAVE_ENTITY_FAILED, "Could not create session with the data " + session.toString());
    }

}
