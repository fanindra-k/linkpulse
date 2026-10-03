package com.linkpulse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // =============================================================
    // Endpoints that never require authentication.
    //
    // WHY A WHITELIST APPROACH?
    // Spring Security's default is deny-all. Explicitly declaring
    // permitted paths makes the security posture clear and auditable.
    // When JWT auth is added (next phase), only these paths will
    // remain open — everything else will require a valid token.
    // =============================================================
    private static final String[] PUBLIC_ENDPOINTS = {
            // Registration and login — must be open before a token exists
            "/v1/auth/**",

            // Swagger / OpenAPI UI — open during development
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Disable CSRF — not needed for stateless REST APIs that use
                // token-based auth (JWT). CSRF protection is only relevant for
                // session-cookie-based apps.
                .csrf(AbstractHttpConfigurer::disable)

                // Stateless session — Spring Security will not create or use
                // an HttpSession. Each request must carry its own credentials.
                // This is mandatory for JWT-based authentication.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                        // Explicitly permit public endpoints
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()

                        // TODO (JWT phase): Replace anyRequest().permitAll() with
                        //   .anyRequest().authenticated()
                        // once JWT filter is wired in. Keeping permitAll() here
                        // now so existing endpoints (bookmarks, etc.) don't break
                        // during development.
                        .anyRequest().permitAll()
                )

                .build();
    }
}
