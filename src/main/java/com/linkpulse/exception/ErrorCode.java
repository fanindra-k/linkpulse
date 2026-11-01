package com.linkpulse.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "Validation failed"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid credentials provided."),
    AUTHENTICATION_REQUIRED(HttpStatus.UNAUTHORIZED, "Authentication required"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access denied"),
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "Bookmark not found"),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "An account with this email already exists"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");

    private final HttpStatus status;
    private final String defaultMessage;
}