package com.linkpulse.bookmark;

public record BookmarkResponse(
        long id,
        String url,
        String title,
        String description,
        String createdAt,
        String updatedAt) {

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
