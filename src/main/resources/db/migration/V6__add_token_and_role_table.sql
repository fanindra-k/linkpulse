CREATE TABLE token (
                       id BIGSERIAL PRIMARY KEY,
                       token VARCHAR(255) NOT NULL,
                       user_id BIGINT NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       expires_at TIMESTAMP NOT NULL,
                       FOREIGN KEY (user_id) REFERENCES auth_user(id)
);

CREATE TABLE role (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL UNIQUE,
                      description VARCHAR(255),
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE auth_user ADD COLUMN role_id BIGINT;
ALTER TABLE auth_user ADD CONSTRAINT fk_auth_user_role FOREIGN KEY (role_id) REFERENCES role(id);
CREATE TABLE auth_user_roles (
                                 auth_user_id BIGINT NOT NULL,
                                 role_id BIGINT NOT NULL,
                                 PRIMARY KEY (auth_user_id, role_id),
                                 FOREIGN KEY (auth_user_id) REFERENCES auth_user(id) ON DELETE CASCADE,
                                 FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);
INSERT INTO role (name, description, created_at)
VALUES
    ('ROLE_USER', 'Default role for all users', CURRENT_TIMESTAMP),
    ('ROLE_ADMIN', 'Administrator role with elevated privileges', CURRENT_TIMESTAMP);
