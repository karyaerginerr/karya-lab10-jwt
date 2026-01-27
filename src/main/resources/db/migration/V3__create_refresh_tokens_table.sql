CREATE TABLE refresh_tokens (
                                id INTEGER PRIMARY KEY AUTOINCREMENT,
                                token TEXT NOT NULL UNIQUE,
                                expiry_date TIMESTAMP NOT NULL,
                                user_id INTEGER NOT NULL,
                                revoked BOOLEAN NOT NULL DEFAULT 0
);
