package com.linkpulse.exception;

public class EmailAlreadyExistException extends ApiException {
    public EmailAlreadyExistException() {
        super(ErrorCode.EMAIL_ALREADY_EXISTS, ErrorCode.EMAIL_ALREADY_EXISTS.getDefaultMessage());
    }
}