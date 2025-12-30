package com.codebyz.simoshbackend.admin;

import com.codebyz.simoshbackend.admin.dto.AdminCreateRequest;
import com.codebyz.simoshbackend.admin.dto.AdminResponse;
import com.codebyz.simoshbackend.admin.dto.AdminUpdateRequest;
import com.codebyz.simoshbackend.exception.ApiException;
import com.codebyz.simoshbackend.user.Role;
import com.codebyz.simoshbackend.user.User;
import com.codebyz.simoshbackend.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AdminResponse create(AdminCreateRequest request) {
        if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
            throw new ApiException(HttpStatus.CONFLICT, "Bu username allaqachon mavjud");
        }
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new ApiException(HttpStatus.CONFLICT, "Bu email allaqachon mavjud");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole(Role.ADMIN);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    public List<AdminResponse> getAll() {
        return userRepository.findAllByRole(Role.ADMIN).stream()
                .map(this::toResponse)
                .toList();
    }
    public AdminResponse getById(UUID id) {
        User user = userRepository.findByIdAndRole(id, Role.ADMIN)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Admin topilmadi"));
        return toResponse(user);
    }

    public AdminResponse update(UUID id, AdminUpdateRequest request) {
        User user = userRepository.findByIdAndRole(id, Role.ADMIN)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Admin topilmadi"));

        if (StringUtils.hasText(request.getUsername())
                && !request.getUsername().equalsIgnoreCase(user.getUsername())) {
            if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
                throw new ApiException(HttpStatus.CONFLICT, "Bu username allaqachon mavjud");
            }
            user.setUsername(request.getUsername());
        }

        if (StringUtils.hasText(request.getEmail())
                && !request.getEmail().equalsIgnoreCase(user.getEmail())) {
            if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
                throw new ApiException(HttpStatus.CONFLICT, "Bu email allaqachon mavjud");
            }
            user.setEmail(request.getEmail());
        }

        if (StringUtils.hasText(request.getPassword())) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        return toResponse(userRepository.save(user));
    }

    public void delete(UUID id) {
        User user = userRepository.findByIdAndRole(id, Role.ADMIN)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Admin topilmadi"));
        userRepository.delete(user);
    }

    private AdminResponse toResponse(User user) {
        return new AdminResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole().name());
    }
}
