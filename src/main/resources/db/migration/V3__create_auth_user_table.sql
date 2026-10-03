-- =============================================================
-- V3: Create the auth_user table
-- =============================================================
--
-- Column length rationale:
--
-- first_name / last_name → VARCHAR(100)
--   Covers every real-world human name including multi-part,
--   hyphenated, and unicode names with generous headroom.
--
-- email → VARCHAR(254)
--   RFC 5321 hard maximum: local part (64) + '@' + domain (255)
--   = 254 total. No valid email address can exceed this value.
--
-- password → VARCHAR(72)
--   BCrypt always produces exactly 60 characters regardless of
--   input length. 72 accommodates all BCrypt variants ($2a$, $2b$,
--   $2y$) with a small safety buffer.
--   IMPORTANT: The application-layer @Size(max=72) on the raw
--   password input is equally critical — BCrypt silently truncates
--   input beyond 72 bytes, meaning two passwords differing only
--   after character 72 would hash identically (silent security flaw).
--
-- NEVER edit this file after it has been applied to any environment.
-- To change the schema, create a new migration file instead.
-- =============================================================

CREATE TABLE auth_user (
    id         INTEGER      NOT NULL PRIMARY KEY,

    first_name VARCHAR(100) NOT NULL,

    -- last_name is intentionally nullable at the DB level
    -- to support future OAuth/social login flows where
    -- last name may not be provided.
    last_name  VARCHAR(100),

    email      VARCHAR(254) NOT NULL,

    -- Stores the BCrypt hash (always 60 chars); 72 gives variant headroom.
    password   VARCHAR(72)  NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Unique constraint on email — this is the DB-level guard against
-- duplicate registrations. The application catches the resulting
-- DataIntegrityViolationException and converts it to a 409 Conflict.
CREATE UNIQUE INDEX idx_auth_user_email ON auth_user (email);
