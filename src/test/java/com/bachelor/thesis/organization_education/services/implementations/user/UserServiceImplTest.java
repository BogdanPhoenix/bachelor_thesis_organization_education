package com.bachelor.thesis.organization_education.services.implementations.user;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import com.bachelor.thesis.organization_education.enums.Role;
import com.bachelor.thesis.organization_education.dto.user.User;
import com.bachelor.thesis.organization_education.dto.user.Token;
import org.springframework.security.core.userdetails.UserDetails;
import com.bachelor.thesis.organization_education.requests.user.UserRequest;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.user.RegistrationRequest;
import com.bachelor.thesis.organization_education.responces.user.RegisteredResponse;
import com.bachelor.thesis.organization_education.services.interfaces.user.TokenService;
import com.bachelor.thesis.organization_education.services.interfaces.user.UserCrudService;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserServiceImplTest {
    private static final RegistrationRequest REGISTRATION_REQUEST = RegistrationRequest
            .builder()
            .username("test@gmail.com")
            .password("queryTy4iop$")
            .matchingPassword("queryTy4iop$")
            .role(Role.SYSTEM_ADMIN)
            .firstName("Ivan")
            .lastName("Ivan")
            .build();
    private static final User USER = User.builder()
            .id(1L)
            .username(REGISTRATION_REQUEST.getUsername())
            .password(REGISTRATION_REQUEST.getPassword())
            .role(REGISTRATION_REQUEST.getRole())
            .build();

    @InjectMocks
    private UserServiceImpl serviceMock;
    @Mock
    private TokenService tokenServiceMock;
    @Mock
    private UserCrudService userCrudServiceMock;

    @Test
    @DisplayName("Check for a NullPointerException when a null value was passed to the request.")
    void testRegistrationThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> serviceMock.registration(null));
    }

    @Test
    @DisplayName("Checking for a DuplicateException when the database already has a user with the specified data.")
    void testRegistrationThrowsDuplicateException() {
        when(userCrudServiceMock.createUser(any(RegistrationRequest.class))).thenThrow(DuplicateException.class);
        assertThrows(DuplicateException.class, () -> serviceMock.registration(REGISTRATION_REQUEST));
    }

    @Test
    @DisplayName("Checking the user registration method for the specified request.")
    void testRegistrationCorrect() {
        when(userCrudServiceMock.createUser(any(RegistrationRequest.class))).thenReturn(USER);
        when(tokenServiceMock.createTokenForUser(any(UserDetails.class))).thenReturn(new RegisteredResponse("", ""));
        serviceMock.registration(REGISTRATION_REQUEST);

        verify(userCrudServiceMock).createUser(any(RegistrationRequest.class));
        verify(tokenServiceMock).createTokenForUser(any(UserDetails.class));
    }

    @Test
    @DisplayName("Check for a NotFindEntityInDataBaseException if the JWT signature is not found in the database.")
    void testVerifyEmailThrowsTokenNotFindEntityInDataBaseException() {
        when(tokenServiceMock.get(anyString())).thenThrow(NotFindEntityInDataBaseException.class);
        assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.verifyEmail(""));
    }

    @Test
    @DisplayName("Checking the user's email verification method.")
    void testVerifyEmailThrowsUserNotFindEntityInDataBaseException() {
        Token token = Token.builder()
                .user(USER)
                .build();

        when(tokenServiceMock.get(anyString())).thenReturn(token);
        doNothing().when(userCrudServiceMock).enable(any(UserRequest.class));

        serviceMock.verifyEmail("");

        verify(tokenServiceMock).get(anyString());
        verify(userCrudServiceMock).enable(any(UserRequest.class));
    }

    @Test
    @DisplayName("Checking for JwtException JWT signature has errors.")
    void testVerifyEmailThrowsJwtException() {
        when(tokenServiceMock.get(anyString())).thenThrow(JwtException.class);
        assertThrows(JwtException.class, () -> serviceMock.verifyEmail(""));
    }
}
