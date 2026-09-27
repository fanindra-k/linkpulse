//package com.linkpulse.bookmark_ai;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
///**
// * Repository: The data access layer for Bookmarks.
// *
// * WHAT IS A REPOSITORY?
// * A Repository is an interface that provides methods to interact
// * with the database. You define the interface — Spring generates
// * the implementation at runtime. Yes, you read that right: you
// * write ZERO implementation code, and you get full CRUD operations.
// *
// * HOW DOES IT WORK?
// * JpaRepository<Bookmark, Long> means:
// *   - Bookmark → the entity type (which table to query)
// *   - Long     → the type of the primary key (id column)
// *
// * You automatically get these methods (no code needed):
// *   - save(bookmark)         → INSERT or UPDATE
// *   - findById(id)           → SELECT WHERE id = ?
// *   - findAll()              → SELECT * (with pagination support)
// *   - deleteById(id)         → DELETE WHERE id = ?
// *   - count()                → SELECT COUNT(*)
// *   - existsById(id)         → SELECT EXISTS(...)
// *
// * CUSTOM QUERIES:
// * Spring can also generate queries from method names!
// * For example, if you add a method named:
// *   List<Bookmark> findByTitleContaining(String keyword)
// * Spring generates: SELECT * FROM bookmarks WHERE title LIKE '%keyword%'
// *
// * This is called "query derivation" — Spring parses the method name
// * and builds the SQL. Mind-blowing when you first see it.
// *
// * WHAT YOU'LL ADD LATER:
// * - Weekend 2: findAllByOrderByCreatedAtDesc() — sorted bookmarks
// * - Weekend 3: findByUserId(Long userId) — user-specific bookmarks
// * - Weekend 2: Page<Bookmark> findAll(Pageable pageable) — pagination
// */
//public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
//
//    // This interface is intentionally empty for now.
//    //
//    // The methods inherited from JpaRepository are enough for Weekend 1.
//    // You'll add custom query methods here as you build new features.
//    //
//    // EXERCISE FOR YOU:
//    // Try adding this method and see what happens:
//    //   List<Bookmark> findByTitleContainingIgnoreCase(String keyword);
//    // Then call it from your service. Spring generates the SQL automatically!
//}
