package com.linkpulse.auth;

import com.linkpulse.utils.BaseResponse;
import lombok.Builder;

@Builder
public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        UserResponse user
) implements BaseResponse {
}

