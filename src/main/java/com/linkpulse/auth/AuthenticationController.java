package com.linkpulse.auth;

import com.linkpulse.utils.Response;
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
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Response<RegistrationResponse>> registerUser(@Valid @RequestBody RegistrationRequest user) {
        Response<RegistrationResponse> response = authenticationService.registerUser(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<Response<LoginResponse>> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        var response = authenticationService.loginUser(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
