package com.codebyz.simoshbackend.auth.dto;

public class MeResponse {

    private String id;
    private String username;
    private String email;
    private String role;

    public MeResponse(String id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
