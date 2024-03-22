package com.bachelor.thesis.organization_education.services.implementations.user;

import com.bachelor.thesis.organization_education.services.interfaces.user.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class LogoutServiceImplTest {
    private static final String CORRECT_TOKEN = "Bearer validToken";

    @InjectMocks
    private LogoutServiceImpl logoutService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private Authentication authentication;
    @Mock
    private TokenService tokenServiceMock;

    @Test
    @DisplayName("Verification of the logout method when the token is valid and corresponds to the registered user.")
    void testLogoutWithValidToken() {
        when(request.getHeader(anyString()))
                .thenReturn(CORRECT_TOKEN);

        doNothing().when(tokenServiceMock).revokeUserToken(anyString());
        logoutService.logout(request, response, authentication);
        verify(tokenServiceMock).revokeUserToken(anyString());

        try(MockedStatic<SecurityContextHolder> holderMockedStatic = mockStatic(SecurityContextHolder.class)){
            holderMockedStatic.verify(SecurityContextHolder::clearContext, never());
        }
    }

    @ParameterizedTest
    @MethodSource("testInputHeaders")
    @DisplayName("Check for an exception when an empty header or a missing token was passed in the request.")
    void testLogoutWithInvalidHeader(String header) {
        when(request.getHeader(anyString()))
                .thenReturn(header);

        logoutService.logout(request, response, authentication);
        verify(tokenServiceMock, never()).revokeUserToken(anyString());

        try(MockedStatic<SecurityContextHolder> holderMockedStatic = mockStatic(SecurityContextHolder.class)){
            holderMockedStatic.verify(SecurityContextHolder::clearContext, never());
        }
    }

    private static @NonNull Stream<Arguments> testInputHeaders() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of("InvalidHeader")
        );
    }
}
