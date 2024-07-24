package com.example.CommentService.service;

import com.example.CommentService.model.Comment;
import com.example.CommentService.repository.CommentRepository;
import com.example.CommentService.service.commentUtils.CommentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentUtils commentUtils;

    public ResponseEntity<?> getComments() {
        return commentRepository.getComments();
    }

    public ResponseEntity<?> getComment(UUID id) {
        return commentRepository.getComment(id);
    }

    public ResponseEntity<?> createComment(Comment comment) {
        if(commentUtils.checkScore(comment.getScore())) {
            return commentRepository.createComment(comment);
        }
        return ResponseEntity.badRequest().body("Данные введены не корректно");

    }

    public ResponseEntity<?> updateComment(UUID id, Comment comment) {
        if(commentUtils.checkScore(comment.getScore())) {
            return commentRepository.updateComment(id, comment);
        }
        return ResponseEntity.badRequest().body("Данные введены не корректно");
    }

    public ResponseEntity<?> deleteComment(UUID id){
        return commentRepository.deleteComment(id);
    }

}
