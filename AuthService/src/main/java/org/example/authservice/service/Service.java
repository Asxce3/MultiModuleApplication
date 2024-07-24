package org.example.authservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.example.authservice.config.AuthConfig;
import org.example.authservice.models.Candidate;
import org.example.authservice.repository.Repository;
import org.example.authservice.service.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;



@Component
public class Service {
    @Autowired
    private AuthConfig authConfig;

    @Autowired
    Repository authRepository;

    Utils utils = new Utils();

    public ResponseEntity<?> checkUser(Candidate candidate) {
        if (utils.validateCandidate(candidate)){

            ResponseEntity<?> responseEntity = authRepository.checkUser(candidate);
            Candidate user = (Candidate) responseEntity.getBody();

            try {
                Algorithm algorithm = Algorithm.HMAC256(authConfig.getKey());

                String token = JWT.create()
                        .withClaim("name", user.getUsername())
                        .withExpiresAt(Instant.ofEpochSecond(Instant.now().getEpochSecond() + 300))
                        .sign(algorithm);

                return ResponseEntity.ok(token);

            }   catch (JWTCreationException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body(e.getMessage());
            }

        }
        else return ResponseEntity.badRequest().body("Username or password is incorrect");
    }

    public ResponseEntity<?> checkJWT(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(authConfig.getKey());
            JWTVerifier verifier = JWT.require(algorithm)
                    .acceptLeeway(300)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);

            System.out.println(decodedJWT.getExpiresAtAsInstant());
            System.out.println(decodedJWT.getClaims());


            return ResponseEntity.ok().body(decodedJWT.getClaims());

        }   catch (JWTVerificationException e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("JWT verification failed");
        }
    }
}
