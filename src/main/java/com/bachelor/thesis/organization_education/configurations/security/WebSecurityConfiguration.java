package com.bachelor.thesis.organization_education.configurations.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import com.bachelor.thesis.organization_education.enums.Role;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

import java.util.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    private static final String[] WHILE_LIST = {
            "/users/register",
            "/users/auth"
    };

    private static final Map<Role, List<RequestMatcherConfig>> ROLE_REQUEST_MATCHERS = Map.of(
            Role.LECTURER, List.of(
                    new RequestMatcherConfig(HttpMethod.GET, "/users/lecturer"),
                    new RequestMatcherConfig(HttpMethod.PUT, "/users/lecturer")
            ),
            Role.UNIVERSITY_ADMIN, List.of(
                    new RequestMatcherConfig(HttpMethod.POST, "/users/register-other/*", "/faculties", "/groups", "/universities", "/disciplines"),
                    new RequestMatcherConfig(HttpMethod.PUT, "/faculties/*", "/groups/*", "/universities/*", "/disciplines/*",
                            "/disciplines/*/connect-with-lecturer/*", "/disciplines/*/disconnect-lecturer/*", "/users/lecturer/*/connect-with-discipline/*",
                            "/users/lecturer/*/disconnect-discipline/*"
                    ),
                    new RequestMatcherConfig(HttpMethod.DELETE, "/faculties/*", "/groups/*", "/universities/*", "/disciplines/*")
            ),
            Role.ADMIN, List.of(
                    new RequestMatcherConfig(HttpMethod.DELETE, "/*/delete/**"),
                    new RequestMatcherConfig(HttpMethod.PUT, "/*/activate/**"),
                    new RequestMatcherConfig(HttpMethod.POST, "/specialties"),
                    new RequestMatcherConfig(HttpMethod.PUT, "/specialties/**")
            )
    );

    private final JwtAuthConverter jwtAuthConverter;
    private final KeycloakLogoutHandler keycloakLogoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(@NonNull HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizeHttpRequestsCustomizer()
                )
                .oauth2ResourceServer(
                        oauth2ResourceServerCustomizer()
                )
                .sessionManagement(
                        sessionManagementCustomizer()
                )
                .logout(
                        logoutCustomizer()
                )
                .cors(cors -> cors.configurationSource(corsConfiguration()))
                .build();
    }

    private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizeHttpRequestsCustomizer() {
        return request -> {
            ROLE_REQUEST_MATCHERS.forEach((role, configs) ->
                    configs.forEach(config ->
                            request.requestMatchers(config.method, config.paths).hasRole(role.name())
                    )
            );

            request
                    .requestMatchers(HttpMethod.POST, WHILE_LIST).permitAll()
                    .anyRequest().authenticated();
        };
    }

    private Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> oauth2ResourceServerCustomizer() {
        return oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter));
    }

    private Customizer<SessionManagementConfigurer<HttpSecurity>> sessionManagementCustomizer() {
        return session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private Customizer<LogoutConfigurer<HttpSecurity>> logoutCustomizer() {
        return logout -> logout.addLogoutHandler(keycloakLogoutHandler);
    }

    private CorsConfigurationSource corsConfiguration() {
        var corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "DELETE", "UPDATE"));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    private record RequestMatcherConfig(HttpMethod method, String... paths) { }
}