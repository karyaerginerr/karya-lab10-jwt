package com.example.karya_lab10.service;

import com.example.karya_lab10.model.RefreshToken;
import com.example.karya_lab10.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    /**
     * Creates a new refresh token for the user.
     * Any previously existing refresh tokens for the user are invalidated.
     */
    @Transactional
    public RefreshToken createRefreshToken(Long userId) {

        refreshTokenRepository.revokeAllByUserId(userId);

        RefreshToken refreshToken = new RefreshToken(
                UUID.randomUUID().toString(),
                Instant.now().plusSeconds(60 * 60 * 24 * 7), // 7 days
                userId
        );

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Rotates a refresh token by revoking the old one
     * and issuing a brand new token.
     */
    @Transactional
    public RefreshToken rotateRefreshToken(String oldToken) {

        RefreshToken existingToken = refreshTokenRepository.findByToken(oldToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (existingToken.isRevoked()) {
            throw new RuntimeException("Refresh token already revoked");
        }

        if (existingToken.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        existingToken.setRevoked(true);
        refreshTokenRepository.save(existingToken);

        return createRefreshToken(existingToken.getUserId());
    }
}
