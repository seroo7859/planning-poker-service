package de.bht.planningpoker.service.exception;

public enum ServiceErrorCode {
    READ_ERROR,
    CREATE_ERROR,
    DELETE_ERROR,
    UPDATE_ERROR,

    NULL_VALUE,
    ENTITY_NOT_FOUND,
    ENTITY_EXISTS,
    SAVE_ENTITY_FAILED,
    DELETE_ENTITY_FAILED,
    PAGINATION_FAILED,

    UNAUTHORIZED
}
