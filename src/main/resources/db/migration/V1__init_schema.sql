-- =============================================================
-- V1: Initial Database Schema
-- Consolidates sequences, tables, constraints, and indexes
-- =============================================================

-- Sequences
CREATE SEQUENCE IF NOT EXISTS bookmark_sequence START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS user_sequence START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS role_seq START WITH 1 INCREMENT BY 50;

-- 1. Role Table
CREATE TABLE role (
    id                  BIGINT PRIMARY KEY,
    name                VARCHAR(255) NOT NULL UNIQUE,
    description         VARCHAR(255),
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date  TIMESTAMP
);

-- 2. Auth User Table
CREATE TABLE auth_user (
    id          INTEGER PRIMARY KEY,
    first_name  VARCHAR(100) NOT NULL,
    last_name   VARCHAR(100),
    email       VARCHAR(254) NOT NULL,
    password    VARCHAR(72)  NOT NULL,
    role_id     BIGINT REFERENCES role(id),
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX idx_auth_user_email ON auth_user (email);

-- 3. User - Role Association Table
CREATE TABLE auth_user_roles (
    auth_user_id BIGINT NOT NULL REFERENCES auth_user(id) ON DELETE CASCADE,
    role_id      BIGINT NOT NULL REFERENCES role(id) ON DELETE CASCADE,
    PRIMARY KEY (auth_user_id, role_id)
);

-- 4. Token Table
CREATE TABLE token (
    id           BIGSERIAL PRIMARY KEY,
    token        VARCHAR(255) NOT NULL,
    user_id      BIGINT NOT NULL REFERENCES auth_user(id),
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at   TIMESTAMP NOT NULL,
    validated_at TIMESTAMP
);

-- 5. Bookmarks Table
CREATE TABLE bookmarks (
    id          BIGINT PRIMARY KEY,
    user_id     BIGINT NOT NULL REFERENCES auth_user(id),
    title       VARCHAR(500) NOT NULL,
    url         VARCHAR(2048) NOT NULL,
    description TEXT,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_bookmarks_url ON bookmarks (url);
CREATE INDEX idx_bookmarks_created_at ON bookmarks (created_at DESC);
CREATE INDEX idx_bookmarks_user_id ON bookmarks (user_id);
