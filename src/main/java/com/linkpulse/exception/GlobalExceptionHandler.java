package com.linkpulse.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
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
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Spring MVC's own exceptions: 400/404/405/415...
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex,
            @Nullable Object body,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        // The base class already built a sanitized ProblemDetail. Reuse its text, never ex.getMessage().
        String detail = body instanceof ProblemDetail pd ? pd.getDetail() : null;
        HttpStatus resolved = HttpStatus.resolve(status.value());
        String code = resolved != null ? resolved.name() : "HTTP_" + status.value();
        return respond(status, code, detail, req(request), null, headers, ex);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        Map<String, String> fields = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> fields.putIfAbsent(e.getField(), e.getDefaultMessage()));
        return respond(status, ErrorCode.VALIDATION_FAILED.name(),
                ErrorCode.VALIDATION_FAILED.getDefaultMessage(), req(request), fields, headers, ex);
    }

    // All domain exceptions: one handler, forever
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApi(ApiException ex, HttpServletRequest req) {
        return respond(ex.getErrorCode(), ex.getMessage(), req, ex);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex, HttpServletRequest req) {
        return respond(ErrorCode.INVALID_CREDENTIALS, null, req, ex);
    }

    @ExceptionHandler(AuthenticationException.class)   // missing/invalid token
    public ResponseEntity<Object> handleAuthentication(AuthenticationException ex, HttpServletRequest req) {
        return respond(ErrorCode.AUTHENTICATION_REQUIRED, null, req, ex);
    }

    @ExceptionHandler(AccessDeniedException.class)     // org.springframework.security.access
    public ResponseEntity<Object> handleDenied(AccessDeniedException ex, HttpServletRequest req) {
        return respond(ErrorCode.ACCESS_DENIED, null, req, ex);
    }

    @ExceptionHandler(Exception.class)                 // safety net
    public ResponseEntity<Object> handleUnexpected(Exception ex, HttpServletRequest req) {
        return respond(ErrorCode.INTERNAL_ERROR, null, req, ex);
    }

    // ---- the only place that builds a response ----
    private ResponseEntity<Object> respond(ErrorCode ec, @Nullable String detail,
                                           HttpServletRequest req, Throwable ex) {
        return respond(
                ec.getStatus(),
                ec.name(),
                detail != null ? detail : ec.getDefaultMessage(),
                req,
                null,
                null,
                ex
        );
    }

    private ResponseEntity<Object> respond(
            HttpStatusCode status,
            String code,
            @Nullable String detail,
            HttpServletRequest req,
            @Nullable Map<String, String> fieldErrors,
            @Nullable HttpHeaders headers, Throwable ex
    ) {
        if (status.is5xxServerError()) {
            log.error("{} {} -> {} [{}]", req.getMethod(), req.getRequestURI(), status.value(), code, ex);
        } else {
            log.warn("{} {} -> {} [{}] {}", req.getMethod(), req.getRequestURI(),
                    status.value(), code, ex.getClass().getSimpleName());
        }
        return ResponseEntity.status(status)
                .headers(headers)
                .header(HttpHeaders.CACHE_CONTROL, "no-store")
                .body(ApiErrorResponse.of(status, code, detail, req.getRequestURI(), fieldErrors));
    }

    private static HttpServletRequest req(WebRequest r) {
        return ((ServletWebRequest) r).getRequest();
    }
}