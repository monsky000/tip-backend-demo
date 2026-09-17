package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.User;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(Map.of(
                "message", "Authenticated successfully with JWT!",
                "username", authentication.getName(),
                "email", ((User) authentication.getPrincipal()).getEmail(),
                "firstname", ((User) authentication.getPrincipal()).getFirstname(),
                "lastname", ((User) authentication.getPrincipal()).getLastname(),
                // "address", ((User) authentication.getPrincipal()).getAddress(),
                "authorities", authentication.getAuthorities()));
    }
}
