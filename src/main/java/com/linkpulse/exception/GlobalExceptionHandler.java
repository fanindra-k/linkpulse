package com.linkpulse.exception;

import com.linkpulse.bookmark.BookmarkNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler: Catches exceptions thrown anywhere in
 * your controllers and converts them into proper HTTP responses.
 *
 * HOW IT WORKS:
 * Without this, if your code throws an exception, Spring returns
 * a generic 500 Internal Server Error with a stack trace. Bad UX.
 *
 * With @RestControllerAdvice, we intercept specific exceptions
 * and return clean, structured error responses.
 *
 * FLOW:
 *   Controller throws BookmarkNotFoundException
 *       ↓
 *   Spring catches it, looks for a matching @ExceptionHandler
 *       ↓
 *   handleBookmarkNotFound() runs, returns 404 with error body
 *       ↓
 *   Client receives: { "status": 404, "error": "Bookmark not found..." }
 *
 * WHY THIS PATTERN IS GREAT FOR INTERVIEWS:
 * It shows you understand error handling at an architectural level —
 * not just try-catch in individual methods, but a centralized,
 * consistent error response strategy across the entire API.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles: BookmarkNotFoundException → 404 Not Found
     *
     * Triggered when BookmarkService can't find a bookmark by ID.
     */
    @ExceptionHandler(BookmarkNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBookmarkNotFound(
            BookmarkNotFoundException ex
    ) {
        log.warn("Bookmark not found: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildErrorBody(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    /**
     * Handles: Validation errors → 400 Bad Request
     *
     * Triggered when @Valid fails on a request body.
     * For example, if the client sends { "url": "", "title": "" },
     * the @NotBlank validations fail, and Spring throws
     * MethodArgumentNotValidException.
     *
     * We extract the field-level errors and return them in a clean format:
     * { "status": 400, "errors": { "url": "URL is required", "title": "..." } }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex
    ) {
        log.warn("Validation failed: {}", ex.getMessage());

        // Extract field-specific error messages
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> body = buildErrorBody(
                HttpStatus.BAD_REQUEST, "Validation failed"
        );
        body.put("errors", fieldErrors);

        return ResponseEntity.badRequest().body(body);
    }

    /**
     * Handles: Everything else → 500 Internal Server Error
     *
     * This is the "safety net." If any unhandled exception reaches
     * here, we log it (for debugging) and return a generic error
     * to the client. We never expose stack traces to the client.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(
            Exception ex
    ) {
        // Log the full stack trace — this is for US to debug
        log.error("Unexpected error occurred", ex);

        // Return a generic message — this is for the CLIENT
        // Never expose internal details (class names, stack traces)
        // because attackers can use that information.
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorBody(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "An unexpected error occurred. Please try again later."
                ));
    }

    /**
     * Builds a consistent error response body.
     *
     * All our error responses follow the same structure:
     * {
     *   "status": 404,
     *   "error": "Not Found",
     *   "message": "Bookmark not found with id: 42",
     *   "timestamp": "2024-01-15T10:30:00Z"
     * }
     *
     * Consistency matters — API consumers can always expect
     * the same error format, making their error handling simpler.
     */
    private Map<String, Object> buildErrorBody(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("timestamp", Instant.now().toString());
        return body;
    }
}
