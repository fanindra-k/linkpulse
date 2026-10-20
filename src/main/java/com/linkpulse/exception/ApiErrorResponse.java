package com.linkpulse.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.net.URI;
import java.time.Instant;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
        URI type,
        String title,
        int status,
        String detail,
        String instance,
        String code,
        Instant timestamp,
        String traceId
) {
}
