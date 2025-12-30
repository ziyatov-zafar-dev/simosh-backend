package com.codebyz.simoshbackend.mail;

public interface MailService {

    void send(String to, String subject, String body);
}
