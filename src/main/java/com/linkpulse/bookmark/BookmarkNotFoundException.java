package com.linkpulse.bookmark;

/**
 * Custom exception for when a bookmark is not found.
 */
public class BookmarkNotFoundException extends RuntimeException {

    private final Long bookmarkId;

    public BookmarkNotFoundException(Long bookmarkId) {
        super("Bookmark not found with id: " + bookmarkId);
        this.bookmarkId = bookmarkId;
    }

    public Long getBookmarkId() {
        return bookmarkId;
    }
}
