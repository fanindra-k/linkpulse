-- =============================================================
-- V2: Create sequence for GenerationType.SEQUENCE
-- =============================================================
--
-- INCREMENT BY 50 matches Hibernate's default allocationSize.
-- This allows Hibernate to fetch 50 IDs at a time from PostgreSQL,
-- reducing database round-trips and enabling batch inserts.
-- =============================================================

CREATE SEQUENCE bookmark_sequence START WITH 50 INCREMENT BY 50;

-- Set sequence value past existing records
SELECT setval('bookmark_sequence', (SELECT COALESCE(MAX(id), 0) + 50 FROM bookmarks));
