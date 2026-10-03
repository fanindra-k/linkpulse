package com.linkpulse.utils;

public class ResponseBuilder {

    public static <T extends BaseResponse> Response<T> build(String message, T response) {

        return Response.<T>builder()
                .message(message)
                .data(response)
                .build();
    }
}
