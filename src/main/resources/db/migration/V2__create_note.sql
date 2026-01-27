CREATE TABLE note (
                      id BIGSERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      content TEXT,
                      user_id BIGINT NOT NULL,

                      CONSTRAINT fk_note_user
                          FOREIGN KEY (user_id)
                              REFERENCES users(id)
                              ON DELETE CASCADE
);
