package com.linkpulse.auth;

public record LoginRequest(
        String email,
        String password
) {
}
