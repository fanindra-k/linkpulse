-- =============================================================
-- V1: Create the bookmarks table
-- =============================================================
--
-- This is a Flyway migration file. Flyway runs this SQL exactly
-- once, in order, and records it in the flyway_schema_history table.
--
-- NAMING CONVENTION:
--   V1__create_bookmarks_table.sql
--   ^  ^^                       ^
--   |  ||                       |
--   |  |+-- description         +-- must be .sql
--   |  +--- double underscore (required separator)
--   +------ version number (must be unique, increasing)
--
-- IMPORTANT: Once a migration is applied, NEVER edit it.
-- If you need to change the schema, create a NEW migration
-- (V2__add_something.sql). This is how professional teams
-- manage database changes safely.
-- =============================================================

CREATE TABLE bookmarks (

    -- Primary key: UUID vs BIGINT
    -- We use BIGSERIAL (auto-incrementing long) for simplicity.
    -- UUID is also common — we'll discuss trade-offs below.
    --
    -- WHY BIGSERIAL?
    -- + Simpler, smaller (8 bytes vs 16 bytes)
    -- + Better index performance (sequential inserts)
    -- + Easier to debug ("bookmark #42" vs "bookmark a1b2c3d4-...")
    --
    -- WHY SOME TEAMS USE UUID?
    -- + No coordination needed in distributed systems
    -- + Can't guess IDs (security — but we'll fix this with auth)
    -- + Merge databases without ID conflicts
    --
    -- For a single-database app, BIGSERIAL is the pragmatic choice.
    id          BIGSERIAL PRIMARY KEY,

    -- The URL that was bookmarked. This is the core data.
    -- VARCHAR(2048) because URLs can be very long.
    -- NOT NULL because a bookmark without a URL is meaningless.
    url         VARCHAR(2048) NOT NULL,

    -- Title of the page. We'll auto-fetch this later,
    -- but the user can also provide it manually.
    title       VARCHAR(500) NOT NULL,

    -- Optional notes the user adds when saving.
    -- TEXT = unlimited length (PostgreSQL stores it efficiently).
    description TEXT,

    -- Timestamps: ALWAYS add these to every table.
    -- You'll thank yourself later when debugging:
    -- "When was this bookmark created? Was it updated after?"
    --
    -- TIMESTAMP WITH TIME ZONE: stores the exact moment in UTC.
    -- Without time zone, you'll have timezone bugs. Always use WITH.
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()

    -- =============================================================
    -- WHAT YOU'LL ADD LATER:
    -- =============================================================
    -- Weekend 3: user_id BIGINT REFERENCES users(id)
    --            → connects bookmarks to users
    -- Weekend 7: summary TEXT, status VARCHAR(20)
    --            → AI-generated summary and processing status
    -- =============================================================
);

-- INDEX: Speed up lookups by URL
-- If a user tries to save the same URL twice, we want to detect it fast.
-- This also speeds up "does this URL already exist?" queries.
CREATE INDEX idx_bookmarks_url ON bookmarks (url);

-- INDEX: Speed up "most recent bookmarks first" queries.
-- Most users want to see their latest bookmarks at the top.
-- Without this index, PostgreSQL would scan the entire table.
CREATE INDEX idx_bookmarks_created_at ON bookmarks (created_at DESC);
