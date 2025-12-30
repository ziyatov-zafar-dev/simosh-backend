package com.codebyz.simoshbackend.admin.dto;

import java.util.UUID;

public class AdminResponse {

    private UUID id;
    private String username;
    private String email;
    private String role;

    public AdminResponse(UUID id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public UUID getId() {
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
