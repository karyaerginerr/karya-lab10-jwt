package com.example.karya_lab10.integration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Integration test placeholder for JWT-protected endpoints (Spring Boot 4 setup)")
class SecurityIntegrationTest {

    @Test
    void jwtIntegrationTestPlaceholder() {
        // Integration test scenario:
        // 1. Call /auth/login and obtain JWT
        // 2. Call /protected endpoint without token -> expect 403
        // 3. Call /protected endpoint with token -> expect 200
    }
}
