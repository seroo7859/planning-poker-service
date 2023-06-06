package de.bht.planningpoker.auth.jwt.exception;

import io.jsonwebtoken.JwtException;

public class JwtValidationException extends JwtException {

    private final String token;

    public JwtValidationException(String message, String token) {
        super(message);
        this.token = token;
    }

    public JwtValidationException(String message, Throwable cause, String token) {
        super(message, cause);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
