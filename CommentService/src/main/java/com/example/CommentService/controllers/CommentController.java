package com.example.CommentService.controllers;

import com.example.CommentService.model.Comment;
import com.example.CommentService.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping()
    public ResponseEntity<?> getMany(){
        return commentService.getComments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable UUID id){
        return commentService.getComment(id);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody() Comment comment){
        return commentService.createComment(comment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable() UUID id, @RequestBody Comment comment) {
        return commentService.updateComment(id, comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable() UUID id ){
        return commentService.deleteComment(id);
    }
}
