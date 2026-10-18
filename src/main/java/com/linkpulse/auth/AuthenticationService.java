package com.linkpulse.auth;

import com.linkpulse.exception.EmailAlreadyExistException;
import com.linkpulse.role.RoleRepository;
import com.linkpulse.user.AuthUser;
import com.linkpulse.user.UserRepository;
import com.linkpulse.utils.Response;
import com.linkpulse.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

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
}
