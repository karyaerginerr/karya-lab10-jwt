package com.example.karya_lab10.auth;

import com.example.karya_lab10.model.AuthResponse;
import com.example.karya_lab10.model.LoginRequest;
import com.example.karya_lab10.model.RegisterRequest;
import com.example.karya_lab10.model.User;
import com.example.karya_lab10.model.RefreshToken;
import com.example.karya_lab10.security.JwtService;
import com.example.karya_lab10.service.UserService;
import com.example.karya_lab10.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(
            JwtService jwtService,
            UserService userService,
            RefreshTokenService refreshTokenService
    ) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
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

        if (!isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password!");
        }

        // ✅ access token
        String accessToken = jwtService.generateToken(request.getEmail());

        // ✅ refresh token (DB’ye kaydedilir)
        User user = userService.findByEmail(request.getEmail());
        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user.getId());

        return ResponseEntity.ok(
                Map.of(
                        "accessToken", accessToken,
                        "refreshToken", refreshToken.getToken()
                )
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String oldRefreshToken = request.get("refreshToken");

        RefreshToken newRefreshToken =
                refreshTokenService.rotateRefreshToken(oldRefreshToken);

        String newAccessToken =
                jwtService.generateToken(newRefreshToken.getUserId().toString());

        return ResponseEntity.ok(
                Map.of(
                        "accessToken", newAccessToken,
                        "refreshToken", newRefreshToken.getToken()
                )
        );
    }
}
