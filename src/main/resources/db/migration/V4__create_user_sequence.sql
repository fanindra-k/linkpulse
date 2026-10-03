-- =============================================================
-- V4: Create sequence for AuthUser GenerationType.SEQUENCE
-- =============================================================
--
-- The AuthUser entity declares:
--   @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 50)
--
-- allocationSize = 50 means Hibernate fetches IDs in batches of 50
-- from the DB, reducing round-trips. The DB sequence must INCREMENT BY 50
-- to stay in sync with Hibernate's hi-lo allocation strategy.
--
-- START WITH 1: New table, so we start from the beginning.
-- =============================================================

CREATE SEQUENCE user_sequence START WITH 1 INCREMENT BY 50;
