package com.example.CommentService.repository.DAO.postgres;

import com.example.CommentService.model.Comment;
import com.example.CommentService.repository.DAO.ObjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CommentDAOImpl implements ObjectDAO<Comment>{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Comment> getMany() {
        List<Comment> comments = jdbcTemplate.query
                ("SELECT * FROM comment", new BeanPropertyRowMapper<>(Comment.class));
        return comments;
    }

    public List<Comment> getManyByRestaurantId(UUID restaurant_id) {
        List<Comment> comments = jdbcTemplate.query
                ("SELECT score FROM comment WHERE restaurant_id = ?",
                        new Object[]{restaurant_id},
                        new BeanPropertyRowMapper<>(Comment.class));
        return comments;
    }

    @Override
    public Optional<Comment> getOne(UUID id) throws Exception{
        return jdbcTemplate.query
                ("SELECT * FROM comment WHERE id = ?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Comment.class)).stream().findAny();
    }

    @Override
    public void create(Comment comment) throws Exception{
        try {
            jdbcTemplate.update("INSERT INTO comment VALUES (gen_random_uuid(), ?, ?, ?, ?)",
                    comment.getPersonId(),
                    comment.getRestaurantId(),
                    comment.getComment(),
                    comment.getScore());

        } catch (DuplicateKeyException e) {
            e.printStackTrace();

            throw new Exception("User already post comment");

        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
//            ПРИ НЕ ПРАВИЛЬНО ВВЕДЕННЫХ ДАННЫХ ВЫБРАСЫВАЕТ ЭТУ ОШИБКУ
            throw new Exception("Some fields are empty");
        }
    }

    @Override
    public void update(UUID id, Comment comment) throws Exception {
        int i = jdbcTemplate.update("UPDATE comment SET comment = ?, score = ? WHERE id = ?",
                comment.getComment(),
                comment.getScore(), id);

        if (i != 1) {
            throw new Exception("Comment not found");
        }
    }

    @Override
    public void delete(UUID id) throws Exception {

        int i = jdbcTemplate.update("DELETE FROM comment WHERE id = ?", id);

        if (i != 1) {
            throw new Exception("Comment not found");
        }
    }

    public void deleteCommentByUserId(UUID personId) throws Exception {
        int i = jdbcTemplate.update("DELETE FROM comment WHERE person_id = ?", personId);

        if (i != 1) {
            throw new Exception("User dont have comment to delete");
        }
    }

    public void deleteCommentByRestaurantId(UUID restaurantId) throws Exception {
        int i = jdbcTemplate.update("DELETE FROM comment WHERE restaurant_id = ?", restaurantId);

        if (i != 1) {
            throw new Exception("Restaurant not found");
        }
    }


}
