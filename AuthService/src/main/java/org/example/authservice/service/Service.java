package org.example.authservice.service;

import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    AuthConfig authConfig;

    @Autowired
    private CandidateImpl candidateImpl;

    Utils utils = new Utils();

    private Algorithm algorithm;

    private final long accessTokenLifetime = 1;  // 5 minute 180

    private final long refreshTokenLifetime = 60;   // 3 month 3600 * 24 * 30

    private final HashMap<String, String> tokens = new HashMap<>();

    public UUID checkUser(Candidate candidate) {
        Optional<Candidate> optCandidate = candidateImpl.checkUser(candidate);

        if (optCandidate.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        algorithm = Algorithm.HMAC256(authConfig.getKey());

        return optCandidate.get().getId();

    }

    public void verifyToken(String token) {
        utils.verifyToken(algorithm, token, accessTokenLifetime);
    }

    public UUID changeTokens(String token) {
        DecodedJWT decodedJWT = utils.verifyToken(algorithm, token, refreshTokenLifetime);

        String id = decodedJWT.getClaim("id").asString();
        UUID userId = UUID.fromString(id);

        Optional<RefreshToken> refreshToken = candidateImpl.getRefreshToken(new RefreshToken(userId, token));

        if (refreshToken.isEmpty()) {
            throw new JWTVerificationException("Invalid refresh token");
        }

        return userId;

    }

    public void setCookieTokens(HashMap<String, String> map, HttpServletResponse response){

        Cookie cookie1 = new Cookie("accessToken", map.get("accessToken"));
        Cookie cookie2 = new Cookie("refreshToken", map.get("refreshToken"));

        cookie1.setMaxAge(60);
        cookie2.setMaxAge(120);

        response.addCookie(cookie1);
        response.addCookie(cookie2);
        response.setContentType("text/plain");
    }

    public HashMap<String, String> createTokens(UUID userId) {
        String accessToken = utils.createJwt(algorithm, userId, accessTokenLifetime);
        String refreshToken = utils.createJwt(algorithm, userId, refreshTokenLifetime);

        candidateImpl.createRefreshToken(new RefreshToken(userId, refreshToken));

        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }



}
