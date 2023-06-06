package de.bht.planningpoker.auth.jwt;

import de.bht.planningpoker.auth.jwt.exception.JwtGenerationException;
import de.bht.planningpoker.auth.jwt.exception.JwtValidationException;
import de.bht.planningpoker.model.User;
import io.jsonwebtoken.JwtException;

public interface JwtService {

    String generateToken(User user) throws JwtGenerationException;

    boolean validateToken(String token) throws JwtValidationException;

    String getSubjectFromToken(String token) throws JwtException;

}
