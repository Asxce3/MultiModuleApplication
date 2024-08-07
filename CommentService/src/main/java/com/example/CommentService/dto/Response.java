package com.example.CommentService.dto;

import java.time.Instant;

public class Response {
    private Instant timestamp = Instant.now();
    private String message;

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getInstant() {
        return timestamp;
    }

    public void setInstant(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
