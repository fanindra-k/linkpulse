package com.linkpulse.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Basic information about a user")
public record UserResponse(
        @Schema(example = "Alex Morgan")
        String displayName,
        @Schema(example = "alex@example.com", format = "email")
        String email
) {
}
