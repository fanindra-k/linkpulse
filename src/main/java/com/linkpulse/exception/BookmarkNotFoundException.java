package com.linkpulse.exception;

public class BookmarkNotFoundException extends ApiException {
    public BookmarkNotFoundException(Long id) {
        super(ErrorCode.BOOKMARK_NOT_FOUND, "Bookmark not found with id: " + id);
    }
}