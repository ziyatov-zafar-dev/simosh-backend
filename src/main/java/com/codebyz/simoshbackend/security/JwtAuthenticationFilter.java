package com.codebyz.simoshbackend.security;

import com.codebyz.simoshbackend.user.User;
import com.codebyz.simoshbackend.user.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractToken(request);
        if (StringUtils.hasText(token)) {
            try {
                Claims claims = jwtService.parseClaims(token);
                String role = claims.get("role", String.class);
                if (role == null) {
                    role = resolveRoleFromDatabase(claims.getSubject());
                }
                if (role != null) {
                    String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;
                    List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(authority));
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception ignored) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header)) {
            String value = header.trim();
            String lower = value.toLowerCase();
            if (lower.startsWith("bearer bearer ")) {
                value = value.substring(14).trim();
            } else if (lower.startsWith("bearer ")) {
                value = value.substring(7).trim();
            }
            return value;
        }
        String param = request.getParameter("accessToken");
        if (!StringUtils.hasText(param)) {
            param = request.getParameter("token");
        }
        return StringUtils.hasText(param) ? param.trim() : null;
    }

    private String resolveRoleFromDatabase(String subject) {
        try {
            UUID userId = UUID.fromString(subject);
            Optional<User> user = userRepository.findById(userId);
            return user.map(value -> value.getRole().name()).orElse(null);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
