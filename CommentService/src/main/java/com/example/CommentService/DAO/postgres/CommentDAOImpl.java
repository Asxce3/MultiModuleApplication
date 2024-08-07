package com.example.CommentService.DAO.postgres;

import com.example.CommentService.DAO.ObjectDAO;
import com.example.CommentService.exceptions.DaoException;
import com.example.CommentService.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CommentDAOImpl implements ObjectDAO<Comment> {

    private final DaoUtils utils = new DaoUtils();
    // Локализация
    private final String errorMessage = "Something went wrong on server";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Comment> getMany() {
        try {
            return jdbcTemplate.query
                    ("SELECT * FROM comment", new BeanPropertyRowMapper<>(Comment.class));
        }   catch (DataAccessException e) {
                throw new DaoException(errorMessage);
        }

    }

    @Override
    public Optional<Comment> getOne(UUID id) {
        try {
            return jdbcTemplate.query
                    ("SELECT * FROM comment WHERE id = ?",
                            new Object[]{id},
                            new BeanPropertyRowMapper<>(Comment.class)).stream().findAny();
        }   catch (DataAccessException e) {
                throw new DaoException(errorMessage);
        }
    }

    @Override
    public void create(Comment comment) {
        try {
            jdbcTemplate.update("INSERT INTO comment VALUES (gen_random_uuid(), ?, ?, ?, ?)",
                    comment.getPersonId(),
                    comment.getRestaurantId(),
                    comment.getComment(),
                    comment.getScore());

        }   catch (DuplicateKeyException e) {
                Optional<String> key = utils.getKeyName(e.getMessage());
                if (key.isPresent()) {
                    throw new DuplicateKeyException("Current " + key.get() + " already exists");
                }

        }   catch (DataIntegrityViolationException e) {
            e.printStackTrace();
                throw new DataIntegrityViolationException("Some fields is empty");

        }   catch (DataAccessException e) {
                throw new DaoException(errorMessage);
        }
    }

    @Override
    public void update(UUID id, Comment comment) {
        try {
            jdbcTemplate.update("UPDATE comment SET comment = ?, score = ? WHERE id = ?",
                    comment.getComment(),
                    comment.getScore(), id);

        }   catch (DuplicateKeyException e) {
                Optional<String> key = utils.getKeyName(e.getMessage());
                if (key.isPresent()) {
                    throw new DuplicateKeyException("Current " + key.get() + " already exists");
                }

        }   catch (DataIntegrityViolationException e) {
                e.printStackTrace();
                throw new DataIntegrityViolationException("Some fields is empty");

        }   catch (DataAccessException e) {
                throw new DaoException(errorMessage);
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            jdbcTemplate.update("DELETE FROM comment WHERE id = ?", id);
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