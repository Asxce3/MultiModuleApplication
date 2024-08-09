package org.example.authservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.example.authservice.DAO.CandidateImpl;
import org.example.authservice.config.AuthConfig;
import org.example.authservice.exceptions.UserNotFoundException;
import org.example.authservice.models.Candidate;
import org.example.authservice.service.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class Service {
    @Autowired
    private AuthConfig authConfig;

    @Autowired
    private CandidateImpl candidateImpl;

    Utils utils = new Utils();

    public String createToken(Candidate candidate) {
        Optional<Candidate> optCandidate = candidateImpl.checkUser(candidate);

        if (optCandidate.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return utils.createJwt(Algorithm.HMAC256(authConfig.getKey()), optCandidate.get());

    }

    public String verifyToken(String token) {
        return utils.verifyToken(Algorithm.HMAC256(authConfig.getKey()), token);
    }
}
