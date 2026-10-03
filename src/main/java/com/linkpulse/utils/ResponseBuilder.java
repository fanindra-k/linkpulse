package com.linkpulse.utils;

import java.time.Instant;

public class ResponseBuilder {

    public static Response build(String message, BaseResponse response) {

        return Response.builder()
                .message(message)
                .timestamp(Instant.now())
                .response(response)
                .build();
    }
}
