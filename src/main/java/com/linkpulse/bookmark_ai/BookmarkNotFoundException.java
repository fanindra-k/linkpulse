package com.linkpulse.bookmark_ai;

/**
 * Custom exception for when a bookmark is not found.
 *
 * WHY A CUSTOM EXCEPTION?
 * Instead of returning null or using generic RuntimeException,
 * we create specific exceptions that:
 * 1. Make the code readable: throw new BookmarkNotFoundException(42)
 * 2. Can be caught by GlobalExceptionHandler to return proper HTTP status
 * 3. Carry meaningful context (which ID was not found)
 *
 * WHY EXTEND RuntimeException (not Exception)?
 * - RuntimeException = unchecked → you don't need try-catch everywhere
 * - Exception = checked → every caller must handle it (verbose, annoying)
 * Spring's @ControllerAdvice catches unchecked exceptions cleanly.
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
