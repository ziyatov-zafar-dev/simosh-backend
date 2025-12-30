package com.codebyz.simoshbackend.exception;

import com.codebyz.simoshbackend.auth.dto.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<MessageResponse> handleApiException(ApiException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<MessageResponse> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(403).body(new MessageResponse("Ruxsat yo'q: " + ex.getMessage()));
    }

    @ExceptionHandler({
            AuthenticationException.class,
            AuthenticationCredentialsNotFoundException.class
    })
    public ResponseEntity<MessageResponse> handleAuthException(Exception ex) {
        return ResponseEntity.status(401).body(new MessageResponse("Autentifikatsiya xatosi: " + ex.getMessage()));
    }
}
