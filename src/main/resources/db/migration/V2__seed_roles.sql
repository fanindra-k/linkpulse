-- =============================================================
-- V2: Initial Seed Data for Roles
-- =============================================================

INSERT INTO role (id, name, description, created_at)
VALUES
    (nextval('role_seq'), 'ROLE_USER', 'Default role for all users', CURRENT_TIMESTAMP),
    (nextval('role_seq'), 'ROLE_ADMIN', 'Administrator role with elevated privileges', CURRENT_TIMESTAMP);
