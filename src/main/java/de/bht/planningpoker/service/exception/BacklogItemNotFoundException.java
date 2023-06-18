package de.bht.planningpoker.service.exception;

public class BacklogItemNotFoundException extends ServiceException {

    public BacklogItemNotFoundException(String number) {
        super(ServiceErrorCode.ENTITY_NOT_FOUND, "Could not find backlog item with the number " + number);
    }

}
