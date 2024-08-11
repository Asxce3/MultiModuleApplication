package org.example.authservice.service;

import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.example.authservice.DAO.CandidateImpl;
import org.example.authservice.config.AuthConfig;
import org.example.authservice.exceptions.UserNotFoundException;
import org.example.authservice.models.Candidate;
import org.example.authservice.models.RefreshToken;
import org.example.authservice.service.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;


@Component
public class Service {

    @Autowired
    private AuthConfig authConfig;

    @Autowired
    private CandidateImpl candidateImpl;

    private final long accessTokenLifetime = 1;  // 5 minute 180

    private final long refreshTokenLifetime = 60;   // 3 month 3600 * 24 * 30

    private final HashMap<String, String> tokens = new HashMap<>();

    Utils utils = new Utils();

    public HashMap<String, String> createToken(Candidate candidate) {
        Optional<Candidate> optCandidate = candidateImpl.checkUser(candidate);

        if (optCandidate.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        Candidate user = optCandidate.get();
        Algorithm algorithm = Algorithm.HMAC256(authConfig.getKey());

        String accessToken = utils.createJwt(algorithm, user.getId(), accessTokenLifetime);
        String refreshToken = utils.createJwt(algorithm, user.getId(), refreshTokenLifetime);

        candidateImpl.createRefreshToken(new RefreshToken(user.getId(), refreshToken));

        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;

    }

    public void verifyToken(String token) {
        utils.verifyToken(Algorithm.HMAC256(authConfig.getKey()), token, accessTokenLifetime);
    }

    public String refreshToken(String token) {
        DecodedJWT decodedJWT = utils.verifyToken(
                Algorithm.HMAC256(authConfig.getKey()),
                token,
                refreshTokenLifetime);
        String id = decodedJWT.getClaim("id").asString();
        UUID personId = UUID.fromString(id);
        Optional<RefreshToken> refreshToken = candidateImpl.checkRefreshToken(new RefreshToken(personId, token));

        if (refreshToken.isEmpty()) {
            throw new JWTVerificationException("Invalid refresh token");
        }
        return utils.createJwt(Algorithm.HMAC256(authConfig.getKey()), personId, accessTokenLifetime);
    }



}
