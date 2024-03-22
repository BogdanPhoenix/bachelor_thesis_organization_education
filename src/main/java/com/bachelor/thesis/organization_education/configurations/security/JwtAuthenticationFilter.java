package com.bachelor.thesis.organization_education.configurations.security;

import com.bachelor.thesis.organization_education.repositories.user.TokenRepository;
import com.bachelor.thesis.organization_education.services.interfaces.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Custom JWT authentication filter that extends OncePerRequestFilter.
 * Responsible for extracting and validating JWT tokens from incoming requests,
 * authenticating users, and authorizing access to protected resources.
 * If a valid token is present and the user is not authenticated,
 * it sets the authentication details in the SecurityContextHolder.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String AUTH_HEADER_PREFIX = "Bearer ";

    @Value("${application.url.main}")
    private String signInURL;

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var servletPath = request.getServletPath();

        if (!servletPath.contains(signInURL) && hasValidHeader(request)){
            authenticateAndAuthorizeRequest(request);
        }

        filterChain.doFilter(request, response);
    }

    private boolean hasValidHeader(@NonNull HttpServletRequest request){
        String authHeader = request.getHeader(AUTH_HEADER_NAME);
        return authHeader != null && authHeader.startsWith(AUTH_HEADER_PREFIX);
    }

    private void authenticateAndAuthorizeRequest(HttpServletRequest request) {
        var jwt = extractJwtFromHeader(request);
        var username = jwtService.extractUsername(jwt);

        if (isUserNotAuthenticated(username)) {
            authorizeRequest(request, jwt, username);
        }
    }

    private @NonNull String extractJwtFromHeader(@NonNull HttpServletRequest request){
        var authHeader = request.getHeader(AUTH_HEADER_NAME);
        return authHeader.substring(AUTH_HEADER_PREFIX.length());
    }

    private boolean isUserNotAuthenticated(String username) {
        return username != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private void authorizeRequest(HttpServletRequest request, String jwt, String userEmail){
        var userDetails = this.userDetailsService.loadUserByUsername(userEmail);

        var isTokenValid = tokenRepository
                .findByAccessToken(jwt)
                .map(token -> !token.isExpired() && !token.isRevoked())
                .orElse(false);

        if(!(jwtService.isTokenValid(jwt, userDetails) && isTokenValid)){
            return;
        }

        var authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
