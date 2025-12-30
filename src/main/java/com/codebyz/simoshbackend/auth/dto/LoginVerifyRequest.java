package com.codebyz.simoshbackend.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginVerifyRequest {

    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String code;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
