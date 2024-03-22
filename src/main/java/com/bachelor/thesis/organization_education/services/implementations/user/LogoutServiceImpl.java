package com.bachelor.thesis.organization_education.services.implementations.user;

import com.bachelor.thesis.organization_education.services.interfaces.user.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutHandler {
    private static final String HEADER = "Authorization";
    private static final String HEADER_START_FROM = "Bearer ";

    private final TokenService tokenService;

    @Override
    public void logout(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Authentication authentication
    ) {
        final String authHeader = request.getHeader(HEADER);

        if(authHeader == null || !authHeader.startsWith(HEADER_START_FROM)) {
            return;
        }

        final String jwt = authHeader.substring(HEADER_START_FROM.length());
        tokenService.revokeUserToken(jwt);
        SecurityContextHolder.clearContext();
    }
}
