package com.linkpulse.auth;

import com.linkpulse.utils.BaseResponse;
import lombok.Builder;

@Builder
public record RegistrationResponse(
        String firstName,
        String lastName,
        String email
) implements BaseResponse {
}
