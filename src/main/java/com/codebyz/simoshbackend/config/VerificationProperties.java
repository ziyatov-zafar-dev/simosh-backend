package com.codebyz.simoshbackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "verification")
public class VerificationProperties {

    private int signUpCodeMinutes;
    private int signInCodeMinutes;
    private Password password = new Password();

    public int getSignUpCodeMinutes() {
        return signUpCodeMinutes;
    }

    public void setSignUpCodeMinutes(int signUpCodeMinutes) {
        this.signUpCodeMinutes = signUpCodeMinutes;
    }

    public int getSignInCodeMinutes() {
        return signInCodeMinutes;
    }

    public void setSignInCodeMinutes(int signInCodeMinutes) {
        this.signInCodeMinutes = signInCodeMinutes;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public static class Password {
        private int forgotPasswordCodeMinutes;
        private int changePasswordCodeMinutes;

        public int getForgotPasswordCodeMinutes() {
            return forgotPasswordCodeMinutes;
        }

        public void setForgotPasswordCodeMinutes(int forgotPasswordCodeMinutes) {
            this.forgotPasswordCodeMinutes = forgotPasswordCodeMinutes;
        }

        public int getChangePasswordCodeMinutes() {
            return changePasswordCodeMinutes;
        }

        public void setChangePasswordCodeMinutes(int changePasswordCodeMinutes) {
            this.changePasswordCodeMinutes = changePasswordCodeMinutes;
        }
    }
}
