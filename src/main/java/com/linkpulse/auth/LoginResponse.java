package com.linkpulse.auth;

import com.linkpulse.utils.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Authentication result containing the JWT used for protected endpoints")
public record LoginResponse(
        @Schema(description = "JWT access token. Use this value with the bearerAuth scheme.", example = "eyJhbGciOiJIUzI1NiJ9...")
        String accessToken,
        @Schema(description = "Authorization scheme", example = "Bearer")
        String tokenType,
        @Schema(description = "Token lifetime in milliseconds", example = "3600000")
        long expiresIn,
        @Schema(description = "Basic information about the authenticated user")
        UserResponse user
) implements BaseResponse {
}
