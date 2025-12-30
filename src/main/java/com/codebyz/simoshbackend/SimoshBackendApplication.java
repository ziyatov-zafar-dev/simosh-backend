package com.codebyz.simoshbackend;

import com.codebyz.simoshbackend.config.JwtProperties;
import com.codebyz.simoshbackend.config.SendGridProperties;
import com.codebyz.simoshbackend.config.StorageProperties;
import com.codebyz.simoshbackend.config.VerificationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        JwtProperties.class,
        SendGridProperties.class,
        VerificationProperties.class,
        StorageProperties.class
})
public class SimoshBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimoshBackendApplication.class, args);
    }
}
