package com.example.UserService.DAO.postgres;


import com.example.UserService.DTO.UserDTO;
import com.example.UserService.exceptions.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class UserDAOImpl {

    private final DaoUtils utils = new DaoUtils();
    // Локализация
    private final String errorMessage = "Something went wrong on server";

    @Autowired
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();


    public List<UserDTO> getMany(){
        try {
            return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(UserDTO.class));

        }   catch (DataAccessException e) {
            e.printStackTrace();
            throw new DaoException(errorMessage);
        }
    }

    public Optional<UserDTO> getOne(UUID id) {
        try {
            return jdbcTemplate.query
                    ("SELECT * FROM Person WHERE id = ?",
                            new Object[]{id},
                            new BeanPropertyRowMapper<>(UserDTO.class)
                    ).stream().findAny();

        }   catch (DataAccessException e) {
                throw new DaoException(errorMessage);
        }

    }


    public void create(UserDTO dto) {
        try {
            jdbcTemplate.update("INSERT INTO Person VALUES(gen_random_uuid(), ?, ?, ?, ?, ?)",
                    dto.getUsername(),
                    dto.getPassword(),
                    dto.getEmail(),
                    dto.getTelephone(),
                    dto.getCountry());

        }   catch (DuplicateKeyException e) {
                Optional<String> key = utils.getKeyName(e.getMessage());
                if (key.isPresent()) {
                    throw new DuplicateKeyException("Current " + key.get() + " already exists");
                }

        }   catch (DataIntegrityViolationException e) {
                throw new DataIntegrityViolationException("Some fields is empty");

        }   catch (DataAccessException e) {
                throw new DaoException(errorMessage);
        }


    }

    public void update(UUID id, UserDTO dto){
        try {
            jdbcTemplate.update("UPDATE Person SET email = ?, telephone = ?, country = ?" +
                            " WHERE id = ?",
                    dto.getEmail(),
                    dto.getTelephone(),
                    dto.getCountry(),
                    id);

        }   catch (DuplicateKeyException e) {
                Optional<String> key = utils.getKeyName(e.getMessage());
                if (key.isPresent()) {
                    throw new DuplicateKeyException("Current " + key.get() + " already exists");
                }

        }   catch (DataIntegrityViolationException e) {
                throw new DataIntegrityViolationException("Сan only change your email and phone number");

        }   catch (DataAccessException e) {
                throw new DaoException(errorMessage);
        }
    }

    public void delete(UUID id){
        try {
            jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);

        }   catch (DataAccessException e) {
                throw new DaoException(errorMessage);
        }

    }
}

class DaoUtils {

    public Optional<String> getKeyName(String message) {
        String keyPattern = "Key \\(([^)]+)\\)=\\(([^)]+)\\)";
        Pattern pattern = Pattern.compile(keyPattern);
        Matcher matcher = pattern.matcher(message);

        if(matcher.find()){
            return Optional.of(matcher.group(1));
        }
        return Optional.empty();

    }

}



