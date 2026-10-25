package com.linkpulse.exception;

import jakarta.annotation.Nullable;

public abstract class ApiException extends RuntimeException {
    private final ErrorCode errorCode;

    protected ApiException(ErrorCode errorCode, String clientMessage) {
        this(errorCode, clientMessage, null);
    }

    protected ApiException(ErrorCode errorCode, String clientMessage, @Nullable Throwable cause) {
        super(clientMessage, cause);          // message = client-facing, cause = diagnostics
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() { return errorCode; }
}