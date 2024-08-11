package org.example.authservice.models;

import java.util.UUID;

public class RefreshToken {
    private UUID id;
    private UUID personId;
    private String refreshToken;

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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public RefreshToken() {}
    public RefreshToken(UUID personId, String refreshToken) {
        this.personId = personId;
        this.refreshToken = refreshToken;

    }
}
