package com.bachelor.thesis.organization_education.configurations.security;

import io.github.cdimascio.dotenv.Dotenv;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {
    private static final String DEFAULT_URL = "/organization-education";

    private static final String[] WHITE_LIST = {
            DEFAULT_URL,
            DEFAULT_URL + "/register",
            DEFAULT_URL + "/register/verifyEmail",
            DEFAULT_URL + "/signout",
            DEFAULT_URL + "/signin"
    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final UserDetailsService userDetailsService;
    private final String rememberMeKey;

    @Autowired
    public WebSecurityConfiguration(
            JwtAuthenticationFilter jwtAuthFilter,
            AuthenticationProvider authenticationProvider,
            LogoutHandler logoutHandler,
            UserDetailsService userDetailsService,
            Dotenv dotenv
    ) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
        this.logoutHandler = logoutHandler;
        this.userDetailsService = userDetailsService;

        this.rememberMeKey = dotenv.get("REMEMBER_ME_KEY");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(@NonNull HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(WHITE_LIST).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login ->
                        login.loginPage("/login")
                                .loginProcessingUrl("/perform_login")
                                .defaultSuccessUrl(DEFAULT_URL)
                                .failureForwardUrl("/login?error=true")
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
                                .logoutSuccessUrl("/login?logout=true")
                )
                .rememberMe(remember ->
                        remember
                                .rememberMeServices(rememberMeServices())
                                .tokenValiditySeconds(604800)
                )
                .exceptionHandling(exception -> exception.accessDeniedPage("/access-denied"))
                .cors(cors -> cors.configurationSource(corsConfiguration()))
                .build();
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        return new TokenBasedRememberMeServices(rememberMeKey, userDetailsService);
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
