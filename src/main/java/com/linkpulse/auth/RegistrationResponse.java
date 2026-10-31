package com.linkpulse.auth;

import com.linkpulse.utils.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Public account details returned after registration")
public record RegistrationResponse(
        @Schema(example = "Alex")
        String firstName,
        @Schema(example = "Morgan")
        String lastName,
        @Schema(example = "alex@example.com", format = "email")
        String email
) implements BaseResponse {
}
