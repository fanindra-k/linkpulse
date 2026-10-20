package com.linkpulse.auth;

import lombok.Builder;

@Builder
public record UserResponse(
        String displayName,
        String email
) {
}
