package com.linkpulse.auth;

import com.linkpulse.exception.EmailAlreadyExistException;
import com.linkpulse.role.RoleRepository;
import com.linkpulse.security.JwtService;
import com.linkpulse.user.AuthUser;
import com.linkpulse.user.UserRepository;
import com.linkpulse.utils.MessagesConstant;
import com.linkpulse.utils.Response;
import com.linkpulse.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final Environment env;


    public Response<RegistrationResponse> registerUser(RegistrationRequest registrationRequest) {
        var userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        var authUser = AuthUser.builder()
                .firstName(registrationRequest.firstName())
                .lastName(registrationRequest.lastName())
                .email(registrationRequest.email())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .roles(List.of(userRole))  // Assign default role
                .build();

        try{
            var savedUser = userRepository.save(authUser);
            var response = RegistrationResponse.builder()
                    .firstName(savedUser.getFirstName())
                    .lastName(savedUser.getLastName())
                    .email(savedUser.getEmail())
                    .build();
            return ResponseBuilder.build("User registered successfully", response);

        }catch(DataIntegrityViolationException ex){
            throw new EmailAlreadyExistException(ex.getMessage());
        }
    }

    public Response<LoginResponse> loginUser(LoginRequest loginRequest) {

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );
        // Continue with the authentication logic
        var claims = new HashMap<String, Object>();
        var user = (AuthUser) auth.getPrincipal();
        claims.put("fullName", user.getName());
        claims.put("email", user.getEmail());
        claims.put("userId", user.getId());
        var jwtToken = jwtService.generateToken(claims, user);
        var response =  LoginResponse.builder()
                .accessToken(jwtToken)
                .tokenType("Bearer")
                .expiresIn(env.getProperty("app.jwt.expiration-ms", Long.class, 3600000L)) // Default to 1 hour if not set
                .user(
                        UserResponse.builder()
                                .displayName(user.getName())
                                .email(user.getEmail())
                                .build()
                )
                .build();
        return buildResponse(response);
    }

    private Response<LoginResponse> buildResponse(LoginResponse data) {
        return Response.<LoginResponse>builder()
                .success(true)
                .message(MessagesConstant.USER_LOGIN_SUCCESSFULLY)
                .data(data)
                .build();
    }
}
