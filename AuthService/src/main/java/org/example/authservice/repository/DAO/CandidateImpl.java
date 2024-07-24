package org.example.authservice.repository.DAO;

import org.example.authservice.models.Candidate;
import org.example.authservice.repository.ObjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CandidateImpl implements ObjectDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public  Optional<Candidate> checkUser(Candidate candidate) {
            return jdbcTemplate.query("SELECT * FROM PERSON WHERE username = ? AND password = ?",
                            new Object[]{candidate.getUsername(), candidate.getPassword()},
                            new BeanPropertyRowMapper<>(Candidate.class) {})
                    .stream().findAny();
    }
}
