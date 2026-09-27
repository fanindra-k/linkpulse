package com.linkpulse.bookmark;

public record BookmarkResponseDto(
        long id,
        String url,
        String title,
        String description,
        String createdAt,
        String updatedAt) {

}
