package com.codebyz.simoshbackend.auth;

import com.codebyz.simoshbackend.auth.dto.AuthResponse;
import com.codebyz.simoshbackend.auth.dto.ForgotPasswordRequest;
import com.codebyz.simoshbackend.auth.dto.LoginRequest;
import com.codebyz.simoshbackend.auth.dto.LoginVerifyRequest;
import com.codebyz.simoshbackend.auth.dto.MeResponse;
import com.codebyz.simoshbackend.auth.dto.MessageResponse;
import com.codebyz.simoshbackend.auth.dto.ResetPasswordRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(@Valid @RequestBody LoginRequest request) {
        authService.login(request.getUsernameOrEmail(), request.getPassword());
        return ResponseEntity.ok(new MessageResponse("Tasdiqlash kodi emailga yuborildi"));
    }

    @PostMapping("/login/verify")
    public ResponseEntity<AuthResponse> verifyLogin(@Valid @RequestBody LoginVerifyRequest request) {
        AuthResponse response = authService.verifyLogin(request.getUsernameOrEmail(), request.getCode());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/password/forgot")
    public ResponseEntity<MessageResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request.getUsernameOrEmail());
        return ResponseEntity.ok(new MessageResponse("Foydalanuvchi mavjud bo'lsa, tasdiqlash kodi yuborildi"));
    }

    @PostMapping("/password/reset")
    public ResponseEntity<MessageResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request.getUsernameOrEmail(), request.getCode(), request.getNewPassword());
        return ResponseEntity.ok(new MessageResponse("Parol muvaffaqiyatli yangilandi"));
    }

    @PostMapping("/me")
    public ResponseEntity<MeResponse> me() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return ResponseEntity.ok(authService.getMe(userId));
    }
}
