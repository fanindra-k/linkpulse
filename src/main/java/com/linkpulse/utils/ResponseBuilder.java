package com.linkpulse.utils;

public class ResponseBuilder {

    public static <T extends BaseResponse> ApiResponse<T> build(String message, T response) {

        return ApiResponse.<T>builder()
                .message(message)
                .data(response)
                .success(true)
                .build();
    }
}
