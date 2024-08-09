package org.example.authservice.service.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.example.authservice.models.Candidate;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
    final String usernameRegex = "^[a-zA-Z0-9._]{3,16}$";

    private boolean checkField(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public boolean validateCandidate(Candidate candidate) {
        try {
            if(!checkField(candidate.getUsername(), usernameRegex)){
                return false;
            }

            if(!checkField(candidate.getPassword(), passwordRegex)){
                return false;
            }

            return true;

        }   catch (Exception e) {
        }
        return false;

    }

    public String createJwt(Algorithm algorithm, Candidate user) {

        try {
            return JWT.create()
                    .withClaim("name", user.getUsername())
                    .withExpiresAt(Instant.ofEpochSecond(Instant.now().getEpochSecond() + 300))
                    .sign(algorithm);

        }   catch (JWTCreationException e) {
            throw new JWTCreationException("JWT creation failed", e.getCause());
        }
    }

    public String verifyToken(Algorithm algorithm, String token) {

        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .acceptLeeway(300)
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);

            return decodedJWT.getClaims().toString();

        }   catch (JWTVerificationException e) {
            throw new JWTVerificationException("JWT verification failed");
        }
    }

}
