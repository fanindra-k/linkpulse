package com.linkpulse.utils;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;

@Data
@Builder
public class Response {
    String message;
    private Instant timestamp;
    BaseResponse response;
}
