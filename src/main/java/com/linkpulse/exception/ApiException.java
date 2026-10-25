package com.linkpulse.exception;

public abstract class ApiException extends RuntimeException {
    private final ErrorCode errorCode;
    protected ApiException(ErrorCode errorCode, String clientMessage) {   // message is CLIENT-FACING
        super(clientMessage);
        this.errorCode = errorCode;
    }
    public ErrorCode getErrorCode() { return errorCode; }
}