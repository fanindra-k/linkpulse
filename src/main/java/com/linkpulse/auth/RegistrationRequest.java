package com.linkpulse.auth;

import com.linkpulse.utils.ValidationUtils;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(
        @NotBlank(message = "First name is required")
        @Size(max = 100, message = "First name must not exceed 100 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 100, message = "Last name must not exceed 100 characters")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 254, message = "Email must not exceed 254 characters")
        String email,

        // min=8: minimum security policy
        // max=72: BCrypt silently truncates input beyond 72 bytes,
        //         so two passwords differing only after char 72 would
        //         produce the same hash — a silent security downgrade.
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 72, message = "Password must be between 8 and 72 characters")
        String password) {

    public RegistrationRequest(
            String firstName,
            String lastName,
            String email,
            String password) {
        this.firstName = ValidationUtils.normalizeText(firstName);
        this.lastName = ValidationUtils.normalizeText(lastName);
        this.email = ValidationUtils.normalizeEmail(email);
        this.password = ValidationUtils.normalizeText(password);
    }
}
