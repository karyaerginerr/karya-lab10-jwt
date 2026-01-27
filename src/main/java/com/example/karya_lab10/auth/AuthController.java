package com.example.karya_lab10.auth;

import com.example.karya_lab10.model.AuthResponse;
import com.example.karya_lab10.model.LoginRequest;
import com.example.karya_lab10.model.RegisterRequest;
import com.example.karya_lab10.model.User;
import com.example.karya_lab10.security.JwtService;
import com.example.karya_lab10.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.createUser(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword()
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AuthResponse(null, "Registration successful!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        boolean isAuthenticated = userService.authenticate(
                request.getEmail(),
                request.getPassword()
        );

        if (isAuthenticated) {
            String token = jwtService.generateToken(request.getEmail());
            return ResponseEntity.ok(new AuthResponse(token, "Login successful!"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password!");
        }
    }
}