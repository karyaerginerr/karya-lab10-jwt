CREATE TABLE refresh_tokens (
                                id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                token VARCHAR(255) NOT NULL UNIQUE,
                                expiry_date TIMESTAMP NOT NULL,
                                user_id BIGINT NOT NULL,
                                revoked BOOLEAN NOT NULL DEFAULT FALSE
);
