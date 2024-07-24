package com.example.CommentService.model;


import java.util.UUID;

public class Comment {

    private UUID id;
    private UUID personId;
    private UUID restaurantId;
    private String comment;
    private int score;

    public Comment(UUID personId, UUID restaurantId, String comment, int score) {
        this.personId = personId;
        this.restaurantId = restaurantId;
        this.comment = comment;
        this.score = score;
    }

    public Comment() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String toString() {
        return "\nComment id= " + id + "\npersonId= " + personId + "\nrestaurantId= " + restaurantId + "\ncomment= " + comment;
    }
}
