package com.linkpulse.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.jspecify.annotations.Nullable;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.net.URI;
import java.time.Instant;
import java.util.Map;

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
        String traceId,
        Map<String, String> errors
) {
    private static final URI ABOUT_BLANK = URI.create("about:blank");
    private static final String GENERIC_5XX = "An unexpected error occurred.";

    public static ApiErrorResponse of(
            HttpStatusCode status,
            String code,
            @Nullable String detail,
            String path,
            @Nullable Map<String, String> errors
    ) {
        HttpStatus resolved = HttpStatus.resolve(status.value());
        String reason = resolved != null ? resolved.getReasonPhrase() : "Error";
        return new ApiErrorResponse(
                ABOUT_BLANK, reason, status.value(),
                status.is5xxServerError() ? GENERIC_5XX : (detail != null ? detail : reason), // invariant: 5xx never leaks
                path, code, Instant.now(), MDC.get("traceId"),
                (errors == null || errors.isEmpty()) ? null : errors);                          // no "errors": {}
    }
}
