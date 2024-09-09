package org.example.authservice.DAO;

import org.example.authservice.exceptions.DaoException;
import org.example.authservice.models.Candidate;
import org.example.authservice.models.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Optional;


@Component
public class CandidateImpl {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private PlatformTransactionManager transactionManager;

    public Optional<Candidate> checkUser(Candidate candidate) {
        try {
            return jdbcTemplate.query("SELECT * FROM PERSON WHERE username = ? AND password = ?",
                            new Object[]{candidate.getUsername(), candidate.getPassword()},
                            new BeanPropertyRowMapper<>(Candidate.class) {
                            })
                    .stream().findAny();

        } catch (DataAccessException e) {
            throw new DaoException("Something went wrong on server");
        }
    }

    public Optional<RefreshToken> getRefreshToken(RefreshToken refreshToken) {
        try {
            return jdbcTemplate.query("SELECT * FROM refresh_token WHERE person_id = ?",
                            new Object[]{refreshToken.getPersonId()},
                            new BeanPropertyRowMapper<>(RefreshToken.class) {
                            })
                    .stream().findAny();

        } catch (DataAccessException e) {
            throw new DaoException("Something went wrong on server");
        }
    }

    @Transactional
    public void createRefreshToken(RefreshToken refreshToken) {
        try {
            jdbcTemplate.update("DELETE FROM refresh_token WHERE person_id = ?", refreshToken.getPersonId());
            jdbcTemplate.update("INSERT INTO refresh_token VALUES (gen_random_uuid(), ?, ?)",
                    refreshToken.getPersonId(),
                    refreshToken.getToken());

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DaoException("Something went wrong on server");
        }
    }


}






