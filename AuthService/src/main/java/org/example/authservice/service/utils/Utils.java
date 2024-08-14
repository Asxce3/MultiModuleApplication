package org.example.authservice.service.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;
import java.util.UUID;


public class Utils {


    public String createJwt(Algorithm algorithm, UUID userId, long timeToLife) {

        try {
            return JWT.create()
                    .withClaim("id", userId.toString())
                    .withExpiresAt(generateTime(timeToLife))
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new JWTCreationException("JWT creation failed", e.getCause());
        }
    }

    public DecodedJWT verifyToken(Algorithm algorithm, String token, long timeToLife) {

        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .acceptLeeway(timeToLife)
                    .build();
            return verifier.verify(token);

        } catch (TokenExpiredException e) {
            throw new JWTVerificationException("Token Invalid");
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("JWT verification failed");
        }
    }


    private Instant generateTime(long time) {
        return Instant.ofEpochSecond(Instant.now().getEpochSecond() + time);
    }


}
