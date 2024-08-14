package org.example.authservice.models;

import java.util.UUID;

public class RefreshToken {
    private UUID id;
    private UUID personId;
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RefreshToken() {}
    public RefreshToken(UUID personId, String token) {
        this.personId = personId;
        this.token = token;

    }
}
