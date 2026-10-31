package com.linkpulse.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Credentials used to authenticate an existing account")
public record LoginRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Must be a valid email format")
        @Schema(description = "Account email address", example = "alex@example.com", format = "email")
        String email,

        @NotBlank(message = "Password is required")
        @Schema(description = "Account password", example = "your-password", format = "password")
        String password
) {
}
