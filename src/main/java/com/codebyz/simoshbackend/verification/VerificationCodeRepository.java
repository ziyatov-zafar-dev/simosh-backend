package com.codebyz.simoshbackend.verification;

import com.codebyz.simoshbackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, UUID> {

    Optional<VerificationCode> findTopByUserAndTypeOrderByCreatedAtDesc(User user, VerificationCodeType type);
}
