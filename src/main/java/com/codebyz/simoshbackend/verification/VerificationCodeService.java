package com.codebyz.simoshbackend.verification;

import com.codebyz.simoshbackend.user.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class VerificationCodeService {

    private static final SecureRandom RANDOM = new SecureRandom();
    private final VerificationCodeRepository repository;

    public VerificationCodeService(VerificationCodeRepository repository) {
        this.repository = repository;
    }

    public VerificationCode create(User user, VerificationCodeType type, int minutes) {
        VerificationCode code = new VerificationCode();
        code.setUser(user);
        code.setType(type);
        code.setCode(generateCode());
        code.setCreatedAt(LocalDateTime.now());
        code.setExpiresAt(LocalDateTime.now().plusMinutes(minutes));
        code.setUsed(false);
        return repository.save(code);
    }

    public VerificationCode getLatest(User user, VerificationCodeType type) {
        return repository.findTopByUserAndTypeOrderByCreatedAtDesc(user, type).orElse(null);
    }

    public boolean isValid(VerificationCode code, String provided) {
        if (code == null || code.isUsed()) {
            return false;
        }
        if (code.getExpiresAt().isBefore(LocalDateTime.now())) {
            return false;
        }
        return code.getCode().equals(provided);
    }

    public void markUsed(VerificationCode code) {
        code.setUsed(true);
        repository.save(code);
    }

    private String generateCode() {
        int value = 100000 + RANDOM.nextInt(900000);
        return String.valueOf(value);
    }
}
