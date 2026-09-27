package com.linkpulse.bookmark_ai;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

/**
 * DTO (Data Transfer Object) for creating a bookmark.
 *
 * WHAT IS A DTO?
 * A DTO is a simple object that carries data between layers.
 * It defines the SHAPE of data your API accepts or returns.
 *
 * WHY NOT USE THE ENTITY DIRECTLY?
 * 1. Your Entity has fields the client shouldn't set (id, createdAt)
 * 2. Your Entity might have fields you don't want to expose (internal status)
 * 3. If you change your DB schema, your API contract stays stable
 * 4. Validation annotations belong on the DTO, not the Entity
 *
 * WHAT IS A JAVA RECORD?
 * A record is a special class introduced in Java 16 that:
 * - Automatically generates constructor, getters, equals, hashCode, toString
 * - Is immutable (fields are final) — once created, can't be changed
 * - Perfect for DTOs because DTOs are just data carriers
 *
 * This record replaces what would have been a 40-line class with
 * constructor, getters, equals, hashCode, and toString methods.
 *
 * VALIDATION ANNOTATIONS:
 * When the controller receives a request, Spring automatically
 * checks these annotations BEFORE your code runs. If validation
 * fails, Spring returns 400 Bad Request without calling your service.
 */
public record CreateBookmarkRequest(

        @NotBlank(message = "URL is required")
        @URL(message = "Must be a valid URL")
        @Size(max = 2048, message = "URL must be under 2048 characters")
        String url,

        @NotBlank(message = "Title is required")
        @Size(max = 500, message = "Title must be under 500 characters")
        String title,

        @Size(max = 5000, message = "Description must be under 5000 characters")
        String description
) {
    // That's it. No boilerplate.
    //
    // Spring's Jackson library automatically converts JSON:
    //   { "url": "https://...", "title": "My Article", "description": "..." }
    // into this record.
    //
    // And the validation annotations ensure:
    //   - url: not blank, must be a valid URL, max 2048 chars
    //   - title: not blank, max 500 chars
    //   - description: optional (no @NotBlank), max 5000 chars
}
