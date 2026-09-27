//package com.linkpulse.bookmark_ai;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.Instant;
//
///**
// * JPA Entity: Represents a row in the "bookmarks" table.
// *
// * WHAT IS AN ENTITY?
// * An Entity is a Java class that maps to a database table.
// * Each instance of this class = one row in the table.
// * Each field = one column.
// *
// * JPA (Java Persistence API) handles the conversion between
// * Java objects and SQL rows. You work with Java objects,
// * and JPA translates your operations into SQL queries.
// *
// * IMPORTANT DESIGN RULE:
// * Never expose Entity objects directly in your API responses.
// * Why? Because:
// * 1. Your DB schema might have fields you don't want to expose
// * 2. Changing your DB schema would break your API contract
// * 3. You might need different response shapes for different endpoints
// *
// * Instead, we use DTOs (Data Transfer Objects) — see BookmarkResponse.java.
// * The Service layer converts Entity ↔ DTO.
// */
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//@Table(name = "bookmarks")
//// @Table explicitly says which table this maps to.
//// Without it, JPA would look for a table named "bookmark" (class name).
//public class Bookmark {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    // IDENTITY = use the database's auto-increment (BIGSERIAL in our SQL).
//    // Other strategies:
//    // SEQUENCE → uses a DB sequence (more control, better for batch inserts)
//    // UUID → generates UUIDs in Java
//    // TABLE → uses a separate table (rarely used)
//    private Long id;
//
//    @Column(nullable = false, length = 2048)
//    // This must match our SQL migration: VARCHAR(2048) NOT NULL
//    // If they don't match, Hibernate's "validate" mode will catch
//    // the mismatch and the app won't start. That's a safety net.
//    private String url;
//
//    @Column(nullable = false, length = 500)
//    private String title;
//
//    @Column(columnDefinition = "TEXT")
//    // columnDefinition = "TEXT" tells JPA to use PostgreSQL's TEXT type
//    // instead of VARCHAR(255) which is the default.
//    private String description;
//
//    @Column(name = "created_at", nullable = false, updatable = false)
//    // updatable = false: once set, JPA won't let you change this.
//    // A creation timestamp should never change.
//    private Instant createdAt;
//
//    @Column(name = "updated_at", nullable = false)
//    private Instant updatedAt;
//
//    // This is the constructor YOUR code will use.
//    // Notice: no 'id' parameter — the database generates it.
//    // Notice: no timestamps — we set them automatically below.
//    public Bookmark(String url, String title, String description) {
//        this.url = url;
//        this.title = title;
//        this.description = description;
//        this.createdAt = Instant.now();
//        this.updatedAt = Instant.now();
//    }
//}
