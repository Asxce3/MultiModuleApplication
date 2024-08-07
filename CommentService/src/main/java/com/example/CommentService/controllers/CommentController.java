package com.example.CommentService.controllers;

import com.example.CommentService.model.Comment;
import com.example.CommentService.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping()
    public List<Comment> getMany(){
        return commentService.getComments();
    }

    @GetMapping("/{id}")
    public Comment getOne(@PathVariable UUID id){
        return commentService.getComment(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody() Comment comment){
        commentService.createComment(comment);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable() UUID id, @RequestBody Comment comment) {
        commentService.updateComment(id, comment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable() UUID id ){
        commentService.deleteComment(id);
    }
}
