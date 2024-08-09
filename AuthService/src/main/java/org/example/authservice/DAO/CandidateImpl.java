package org.example.authservice.DAO;

import org.example.authservice.exceptions.DaoException;
import org.example.authservice.models.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class CandidateImpl {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public Optional<Candidate> checkUser(Candidate candidate) {
        try {
            return jdbcTemplate.query("SELECT * FROM PERSON WHERE username = ? AND password = ?",
                            new Object[]{candidate.getUsername(), candidate.getPassword()},
                            new BeanPropertyRowMapper<>(Candidate.class) {})
                    .stream().findAny();

        }   catch (DataAccessException e) {
            throw new DaoException("Something went wrong on server");
        }

    }
}


