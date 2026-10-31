package com.linkpulse.auth;

import com.linkpulse.utils.ValidationUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Information required to create a user account")
public record RegistrationRequest(
        @NotBlank(message = "First name is required")
        @Size(max = 100, message = "First name must not exceed 100 characters")
        @Schema(description = "User's first name", example = "Alex", maxLength = 100)
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 100, message = "Last name must not exceed 100 characters")
        @Schema(description = "User's last name", example = "Morgan", maxLength = 100)
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 254, message = "Email must not exceed 254 characters")
        @Schema(description = "Unique account email address", example = "alex@example.com", format = "email", maxLength = 254)
        String email,

        // min=8: minimum security policy
        // max=72: BCrypt silently truncates input beyond 72 bytes,
        //         so two passwords differing only after char 72 would
        //         produce the same hash — a silent security downgrade.
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 72, message = "Password must be between 8 and 72 characters")
        @Schema(description = "Password (8–72 characters)", example = "your-password", format = "password", minLength = 8, maxLength = 72)
        String password
) {

    public RegistrationRequest(
            String firstName,
            String lastName,
            String email,
            String password) {
        this.firstName = ValidationUtils.normalizeText(firstName);
        this.lastName = ValidationUtils.normalizeText(lastName);
        this.email = ValidationUtils.normalizeEmail(email);
        this.password = password;
    }
}
