package com.example.CommentService.exceptions;

import java.util.NoSuchElementException;

public class CommentNotFoundException extends NoSuchElementException {
    public CommentNotFoundException(String e) {
        super(e);
    }
}
