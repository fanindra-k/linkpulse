package com.linkpulse.auth;

import com.linkpulse.exception.EmailAlreadyExistException;
import com.linkpulse.user.AuthUser;
import com.linkpulse.user.UserRepository;
import com.linkpulse.utils.Response;
import com.linkpulse.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public Response register(RegistrationRequest registrationRequest) {

        var authUser = AuthUser.builder()
                .firstName(registrationRequest.firstName())
                .lastName(registrationRequest.lastName())
                .email(registrationRequest.email())
                .password(passwordEncoder.encode(registrationRequest.password()))
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
