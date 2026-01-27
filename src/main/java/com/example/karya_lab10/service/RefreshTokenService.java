package com.example.karya_lab10.service;

import com.example.karya_lab10.model.RefreshToken;
import com.example.karya_lab10.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken(
                UUID.randomUUID().toString(),
                Instant.now().plusSeconds(60 * 60 * 24 * 7), // 7 gün
                userId
        );

        return refreshTokenRepository.save(refreshToken);
    }
}
