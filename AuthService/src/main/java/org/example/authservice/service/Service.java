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
    private CandidateImpl candidateImpl;

    private final Utils utils = new Utils();

    private final long ACCESS_TOKEN_LIFE_TIME = 60;  // 5 minute 180

    private final long REFRESH_TOKEN_LIFE_TIME = 120;   // 3 month 3600 * 24 * 30

    private final HashMap<String, String> TOKENS = new HashMap<>();

    private final Algorithm algorithm ;

    public Service(AuthConfig authConfig) {
        algorithm = Algorithm.HMAC256(authConfig.getKey());
    }

    public void verifyAccessToken(String token) {
        utils.verifyToken(algorithm, token, ACCESS_TOKEN_LIFE_TIME);
    }

    public UUID checkUser(Candidate candidate) {
        Optional<Candidate> optCandidate = candidateImpl.checkUser(candidate);

        if (optCandidate.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        return optCandidate.get().getId();
    }

    public UUID changeTokens(String token) {
        DecodedJWT decodedJWT = utils.verifyToken(algorithm, token, REFRESH_TOKEN_LIFE_TIME);

        String id = decodedJWT.getClaim("id").asString();
        UUID userId = UUID.fromString(id);

        Optional<RefreshToken> refreshToken = candidateImpl.getRefreshToken(new RefreshToken(userId, token));

        if (refreshToken.isEmpty()) {
            throw new JWTVerificationException("Invalid refresh token");
        }

        return userId;
    }

    public void setCookieTokens(HashMap<String, String> map, HttpServletResponse response) {

        Cookie cookie1 = new Cookie("accessToken", map.get("accessToken"));
        Cookie cookie2 = new Cookie("refreshToken", map.get("refreshToken"));

        response.addCookie(cookie1);
        response.addCookie(cookie2);
        response.setContentType("text/plain");
    }

    public HashMap<String, String> createTokens(UUID userId) {
        String accessToken = utils.createJwt(algorithm, userId, ACCESS_TOKEN_LIFE_TIME);
        String refreshToken = utils.createJwt(algorithm, userId, REFRESH_TOKEN_LIFE_TIME);

        candidateImpl.createRefreshToken(new RefreshToken(userId, refreshToken));

        TOKENS.put("accessToken", accessToken);
        TOKENS.put("refreshToken", refreshToken);
        return TOKENS;
    }


}
