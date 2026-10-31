package com.linkpulse.bookmark;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A saved bookmark")
public record BookmarkResponse(
        @Schema(description = "Bookmark identifier", example = "42")
        long id,
        @Schema(description = "Bookmarked URL", example = "https://spring.io/projects/spring-boot", format = "uri")
        String url,
        @Schema(description = "Bookmark title", example = "Spring Boot")
        String title,
        @Schema(description = "Optional notes or summary", example = "Documentation for building Spring applications.")
        String description,
        @Schema(description = "Creation timestamp in ISO-8601 format", example = "2026-10-31T10:00:00Z", format = "date-time")
        String createdAt,
        @Schema(description = "Last update timestamp in ISO-8601 format", example = "2026-10-31T10:00:00Z", format = "date-time")
        String updatedAt) {

    /**
     * Converts a bookmark entity to its API response representation.
     * Centralizes the mapping to keep response construction consistent across callers.
     */

    public static BookmarkResponse from(Bookmark bookmark) {
        return new BookmarkResponse(
                bookmark.getId(),
                bookmark.getUrl(),
                bookmark.getTitle(),
                bookmark.getDescription(),
                bookmark.getCreatedAt().toString(),
                bookmark.getUpdatedAt().toString());
    }

}
