package com.example.karya_lab10.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private boolean revoked = false;

    public RefreshToken() {
    }

    public RefreshToken(String token, Instant expiryDate, Long userId) {
        this.token = token;
        this.expiryDate = expiryDate;
        this.userId = userId;
        this.revoked = false;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }
}
