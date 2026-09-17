package com.example.backend.service;

import com.example.backend.dto.AuthResponse;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthResponse register(RegisterRequest request) {
                if (userRepository.existsByUsername(request.getUsername())) {
                        throw new IllegalArgumentException("Username is already taken: " + request.getUsername());
                }

                if (userRepository.existsByEmail(request.getEmail())) {
                        throw new IllegalArgumentException("Email is already registered: " + request.getEmail());
                }

                User user = User.builder()
                                .firstname(request.getFirstname())
                                .lastname(request.getLastname())
                                .username(request.getUsername())
                                .email(request.getEmail())
                                .address(request.getAddress())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(Role.ROLE_USER)
                                .build();

                userRepository.save(user);

                String jwtToken = jwtService.generateToken(user);

                return AuthResponse.builder()
                                .token(jwtToken)
                                .username(user.getUsername())
                                .role(user.getRole().name())
                                .address(user.getAddress())
                                .message("User registered successfully")
                                .build();
        }

        public AuthResponse login(LoginRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getUsername(),
                                                request.getPassword()));

                User user = userRepository.findByUsername(request.getUsername())
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "User not found with username: " + request.getUsername()));

                String jwtToken = jwtService.generateToken(user);

                return AuthResponse.builder()
                                .token(jwtToken)
                                .username(user.getUsername())
                                .role(user.getRole().name())
                                .message("Login successful")
                                .build();
        }
}
