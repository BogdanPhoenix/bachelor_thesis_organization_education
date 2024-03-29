package com.bachelor.thesis.organization_education.configurations.security;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

    private static final String[] WHITE_LIST = {
            "/register/**",
            "/users/**",
            "/roles/**",
            "/login/**"
    };

    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final JwtAuthConverter jwtAuthConverter;

    @Autowired
    public WebSecurityConfiguration(
            AuthenticationProvider authenticationProvider,
            LogoutHandler logoutHandler,
            JwtAuthConverter jwtAuthConverter
    ) {
        this.authenticationProvider = authenticationProvider;
        this.logoutHandler = logoutHandler;
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(@NonNull HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(WHITE_LIST).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                )
                .formLogin(login ->
                        login.loginPage("/login")
                                .loginProcessingUrl("/perform_login")
                                .defaultSuccessUrl("/")
                                .failureForwardUrl("/login?error=true")
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .logout(logout ->
                        logout.logoutUrl("/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
                                .logoutSuccessUrl("/login?logout=true")
                )
                .exceptionHandling(exception -> exception.accessDeniedPage("/access-denied"))
                .cors(cors -> cors.configurationSource(corsConfiguration()))
                .build();
    }
    @Bean
    public CorsConfigurationSource corsConfiguration() {
        var corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "DELETE", "UPDATE"));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}