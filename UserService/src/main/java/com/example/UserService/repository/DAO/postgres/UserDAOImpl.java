package com.example.UserService.repository.DAO.postgres;

import com.example.UserService.model.User;
import com.example.UserService.repository.DAO.ObjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserDAOImpl implements ObjectDAO<User> {

    @Autowired
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Override
    public List<User> getMany() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public Optional<User> getOne(UUID id) throws Exception {
        return jdbcTemplate.query
                ("SELECT * FROM Person WHERE id = ?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(User.class)
                ).stream().findAny();
    }

    @Override
    public void create(User user) throws Exception{
        try {
            jdbcTemplate.update("INSERT INTO Person VALUES(gen_random_uuid(), ?, ?, ?, ?, ?)",
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getTelephone(),
                    user.getCountry());
        }   catch (Exception e) {
            throw new Exception("User already exist");
        }

    }

    @Override
    public void update(UUID id, User user) throws Exception{

        jdbcTemplate.update("UPDATE Person SET email = ?, telephone = ?, country = ?" +
                        " WHERE id = ?",
                user.getEmail(),
                user.getTelephone(),
                user.getCountry(),
                id);

    }

    @Override
    public void delete(UUID id) throws Exception{
        int i = jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
        if (i != 1) {
            throw new Exception("User not found");
        }


    }
}



