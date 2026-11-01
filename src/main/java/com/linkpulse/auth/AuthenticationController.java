package com.linkpulse.auth;

import com.linkpulse.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(
            summary = "Register a user",
            description = "Creates a user account with the standard user role. Email must be unique."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Account created"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Request validation failed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "An account with this email already exists")
    })
    public ResponseEntity<ApiResponse<RegistrationResponse>> registerUser(@Valid @RequestBody RegistrationRequest user) {
        ApiResponse<RegistrationResponse> response = authenticationService.registerUser(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    @Operation(
            summary = "Sign in",
            description = "Authenticates with email and password. Copy data.accessToken from the response and use it in Swagger's Authorize dialog."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Authentication succeeded; response includes a JWT access token"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Request validation failed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Email or password is incorrect")
    })
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        var response = authenticationService.authenticate(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
