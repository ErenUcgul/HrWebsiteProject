package com.hrproject.hrwebsiteproject.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.model.enums.EUserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class JwtManager {
    @Value("${hrwebsite.jwt.secret-key}")
    private String secretKey;
    @Value("${hrwebsite.jwt.issuer}")
    private String issuer;
    private Long expTime = 900L;

    public String generateToken(Long userId, EUserRole role) {
        Algorithm algoritm = Algorithm.HMAC512(secretKey);
        String token = JWT.create()
                .withAudience()
                .withIssuer(issuer)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(expTime))
                .withClaim("userId", userId)
                .withClaim("role", "admin")
                .withClaim("key", "value123")
                .sign(algoritm);
        return token;
    }

    public String generateAccessToken(Long userId, EUserRole role) {
        return JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(expTime))
                .withClaim("userId", userId)
                .withClaim("role", role.name())
                .sign(Algorithm.HMAC512(secretKey));
    }

    public Optional<Long> validateToken(String token) {
        try {
            Algorithm algoritm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algoritm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null) {
                return Optional.empty();
            }
            Long userId = decodedJWT.getClaim("userId").asLong();
            return Optional.of(userId);
        } catch (IllegalArgumentException | JWTVerificationException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public Long getUserIdFromToken(String token) {
        return validateToken(token)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.INVALID_TOKEN));
    }
}
