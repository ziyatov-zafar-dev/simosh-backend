package com.codebyz.simoshbackend.auth;

import com.codebyz.simoshbackend.auth.dto.AuthResponse;
import com.codebyz.simoshbackend.auth.dto.MeResponse;
import com.codebyz.simoshbackend.config.VerificationProperties;
import com.codebyz.simoshbackend.exception.ApiException;
import com.codebyz.simoshbackend.mail.MailService;
import com.codebyz.simoshbackend.security.JwtService;
import com.codebyz.simoshbackend.user.User;
import com.codebyz.simoshbackend.user.UserRepository;
import com.codebyz.simoshbackend.verification.VerificationCode;
import com.codebyz.simoshbackend.verification.VerificationCodeService;
import com.codebyz.simoshbackend.verification.VerificationCodeType;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeService verificationCodeService;
    private final VerificationProperties verificationProperties;
    private final MailService mailService;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       VerificationCodeService verificationCodeService,
                       VerificationProperties verificationProperties,
                       MailService mailService,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationCodeService = verificationCodeService;
        this.verificationProperties = verificationProperties;
        this.mailService = mailService;
        this.jwtService = jwtService;
    }

    public void login(String usernameOrEmail, String password) {
        User user = userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCase(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Login yoki parol noto'g'ri"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Login yoki parol noto'g'ri");
        }

        VerificationCode code = verificationCodeService.create(
                user,
                VerificationCodeType.SIGN_IN,
                verificationProperties.getSignInCodeMinutes()
        );
        mailService.send(user.getEmail(), "Kirish kodi", "Kirish uchun tasdiqlash kodi: " + code.getCode());
    }

    public AuthResponse verifyLogin(String usernameOrEmail, String code) {
        User user = userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCase(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Login yoki parol noto'g'ri"));

        VerificationCode latest = verificationCodeService.getLatest(user, VerificationCodeType.SIGN_IN);
        if (!verificationCodeService.isValid(latest, code)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Tasdiqlash kodi noto'g'ri yoki muddati o'tgan");
        }
        verificationCodeService.markUsed(latest);

        String accessToken = jwtService.generateAccessToken(
                user.getId().toString(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
        String refreshToken = jwtService.generateRefreshToken(user.getId().toString());

        return new AuthResponse(accessToken, refreshToken, user.getRole().name());
    }

    public void forgotPassword(String usernameOrEmail) {
        User user = userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCase(usernameOrEmail, usernameOrEmail)
                .orElse(null);
        if (user == null) {
            return;
        }

        VerificationCode code = verificationCodeService.create(
                user,
                VerificationCodeType.FORGOT_PASSWORD,
                verificationProperties.getPassword().getForgotPasswordCodeMinutes()
        );
        mailService.send(user.getEmail(), "Parolni tiklash kodi", "Parolni tiklash kodi: " + code.getCode());
    }

    public void resetPassword(String usernameOrEmail, String code, String newPassword) {
        User user = userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCase(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Foydalanuvchi topilmadi"));

        VerificationCode latest = verificationCodeService.getLatest(user, VerificationCodeType.FORGOT_PASSWORD);
        if (!verificationCodeService.isValid(latest, code)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Tasdiqlash kodi noto'g'ri yoki muddati o'tgan");
        }
        verificationCodeService.markUsed(latest);

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public MeResponse getMe(String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Foydalanuvchi topilmadi"));
        return new MeResponse(user.getId().toString(), user.getUsername(), user.getEmail(), user.getRole().name());
    }
}
