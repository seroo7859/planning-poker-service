package de.bht.planningpoker.auth.jwt;

import de.bht.planningpoker.auth.jwt.exception.JwtGenerationException;
import de.bht.planningpoker.auth.jwt.exception.JwtValidationException;
import de.bht.planningpoker.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.*;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.audience}")
    private String audience;

    @Value("${jwt.validity}")
    private Duration validity;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    @Override
    public String generateToken(User user) throws JwtGenerationException {
        if (Objects.isNull(user)) {
            throw new JwtGenerationException("User may not be null");
        }

        Map<String, Object> header = new HashMap<>();
        header.put(JwsHeader.ALGORITHM, SignatureAlgorithm.HS256.getValue());
        header.put(JwsHeader.TYPE, JwsHeader.JWT_TYPE);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Claims.ID, UUID.randomUUID().toString());
        claims.put(Claims.ISSUER, issuer);
        claims.put(Claims.SUBJECT, user.getId());
        claims.put("name", user.getUsername());
        claims.put("role", user.getRole().toString());
        claims.put(Claims.AUDIENCE, audience);
        claims.put(Claims.ISSUED_AT, new Date(System.currentTimeMillis()));
        claims.put(Claims.NOT_BEFORE, new Date(System.currentTimeMillis()));
        claims.put(Claims.EXPIRATION, new Date(System.currentTimeMillis() + validity.toMillis()));

        try {
            return Jwts.builder()
                    .setHeader(header)
                    .setClaims(claims)
                    .signWith(getKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (InvalidKeyException e) {
            throw new JwtGenerationException("Token key is invalid", e);
        } catch (JwtException e) {
            throw new JwtGenerationException("Token generation failed", e);
        } catch (Exception e) {
            throw new JwtGenerationException(e.getMessage(), e);
        }
    }

    @Override
    public boolean validateToken(String token) throws JwtValidationException {
        try {
            Claims claims = getClaimsFromToken(token);
            return !isExpired(claims.getExpiration());
        } catch (ExpiredJwtException e) {
            throw new JwtValidationException("Token is expired", e, token);
        } catch (SignatureException e) {
            throw new JwtValidationException("Token signature validation failed", e, token);
        } catch (MissingClaimException e) {
            throw new JwtValidationException("Token has missing claim '" + e.getClaimName() + "'", e, token);
        } catch (IncorrectClaimException e) {
            throw new JwtValidationException("Token has claim '" + e.getClaimName() + "', but its value was not the expected value", e, token);
        } catch (InvalidClaimException e) {
            throw new JwtValidationException("Token has missing claim '" + e.getClaimName() + "' or did not have the expected value", e, token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new JwtValidationException("Token is invalid", e, token);
        } catch (JwtException e) {
            throw new JwtValidationException("Token validation failed", e, token);
        } catch (Exception e) {
            throw new JwtValidationException(e.getMessage(), e, token);
        }
    }

    private Jws<Claims> decodeToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .requireIssuer(issuer)
                .requireAudience(audience)
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);
    }

    private Claims getClaimsFromToken(String token) throws JwtException {
        return decodeToken(token)
                .getBody();
    }

    private boolean isExpired(Date expiration) {
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    @Override
    public String getSubjectFromToken(String token) throws JwtException {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

}
