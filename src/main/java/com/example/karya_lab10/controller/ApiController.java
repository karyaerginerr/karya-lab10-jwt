package com.example.karya_lab10.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import com.example.karya_lab10.model.RegisterRequest;

@RestController
public class ApiController {

    @GetMapping("/api/info")
    public Map<String, String> getInfo(
            @RequestHeader("User-Agent") String userAgent) {

        Map<String, String> response = new HashMap<>();
        response.put("userAgent", userAgent);
        return response;
    }

    // ✅ POST METHOD MUST BE INSIDE THE CLASS
    @PostMapping(
            value = "/api/register",
            consumes = "application/json"
    )
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("Username is required");
        }

        if (request.getEmail() == null || !request.getEmail().contains("@")) {
            return ResponseEntity.badRequest().body("Invalid email");
        }

        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/api/private")
    public ResponseEntity<String> privateEndpoint(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (authHeader == null || authHeader.isEmpty()) {
            return ResponseEntity
                    .status(401)
                    .body("Unauthorized: missing Authorization header");
        }

        return ResponseEntity.ok("You are authenticated");
    }

    @GetMapping("/api/admin")
    public ResponseEntity<String> adminEndpoint(
            @RequestHeader(value = "Role", required = false) String role) {

        if (role == null) {
            return ResponseEntity
                    .status(401)
                    .body("Unauthorized: no role provided");
        }

        if (!role.equals("ADMIN")) {
            return ResponseEntity
                    .status(403)
                    .body("Forbidden: admin access only");
        }

        return ResponseEntity.ok("Welcome admin");
    }


}
