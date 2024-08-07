package com.example.CommentService.service;

import com.example.CommentService.DAO.postgres.CommentDAOImpl;
import com.example.CommentService.exceptions.CommentNotFoundException;
import com.example.CommentService.model.Comment;
import com.example.CommentService.service.commentUtils.CommentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CommentService {

    @Autowired
    CommentDAOImpl commentDAO;

    @Autowired
    CommentUtils commentUtils;

    public List<Comment> getComments() {
        return commentDAO.getMany();
    }

    public Comment getComment(UUID id) {
        Optional<Comment> comment = commentDAO.getOne(id);
        if(comment.isEmpty()) {
            throw new CommentNotFoundException("Comment not found");

        }
        return comment.get();
    }

    public void createComment(Comment comment) {
        if(commentUtils.checkScore(comment.getScore())) {
            commentDAO.create(comment);
        }
    }

    public void updateComment(UUID id, Comment comment) {
        if( commentUtils.checkScore(comment.getScore())) {  // По отдельности добавляет только score. сообщение нет
            commentDAO.update(id, comment);
        }
    }

    public void deleteComment(UUID id){
        commentDAO.delete(id);
    }

}
