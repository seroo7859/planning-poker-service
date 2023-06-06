package de.bht.planningpoker.auth.jwt.exception;

import io.jsonwebtoken.JwtException;

public class JwtGenerationException extends JwtException {

    public JwtGenerationException(String message) {
        super(message);
    }

    public JwtGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

}
