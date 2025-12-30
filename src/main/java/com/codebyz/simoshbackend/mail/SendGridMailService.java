package com.codebyz.simoshbackend.mail;

import com.codebyz.simoshbackend.config.SendGridProperties;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SendGridMailService implements MailService {

    private static final Logger log = LoggerFactory.getLogger(SendGridMailService.class);
    private final SendGridProperties properties;

    public SendGridMailService(SendGridProperties properties) {
        this.properties = properties;
    }

    @Override
    public void send(String to, String subject, String body) {
        if (!properties.isEnabled()) {
            log.info("SendGrid disabled. Email to {} with subject '{}' not sent.", to, subject);
            return;
        }
        try {
            Email from = new Email(properties.getFromEmail(), properties.getFromName());
            Email recipient = new Email(to);
            Content content = new Content("text/plain", body);
            Mail mail = new Mail(from, subject, recipient, content);

            SendGrid sg = new SendGrid(properties.getApiKey());
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);
            if (response.getStatusCode() >= 400) {
                log.warn("SendGrid error status={} body={}", response.getStatusCode(), response.getBody());
            }
        } catch (Exception ex) {
            log.warn("SendGrid send failed", ex);
        }
    }
}
