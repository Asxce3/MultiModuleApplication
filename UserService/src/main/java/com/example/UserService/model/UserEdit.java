package com.example.UserService.model;

public class UserEdit extends User {

    private String email;
    private String telephone;

    public String toString() {
        return "email: " + email + ", telephone: " + telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
