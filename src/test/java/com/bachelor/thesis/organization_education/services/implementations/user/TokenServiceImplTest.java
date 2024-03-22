package com.bachelor.thesis.organization_education.services.implementations.user;

import org.mockito.Mock;
import java.util.Optional;
import java.util.ArrayList;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import com.bachelor.thesis.organization_education.enums.Role;
import com.bachelor.thesis.organization_education.dto.user.User;
import com.bachelor.thesis.organization_education.dto.user.Token;
import com.bachelor.thesis.organization_education.enums.TokenType;
import com.bachelor.thesis.organization_education.requests.user.UserRequest;
import com.bachelor.thesis.organization_education.responces.user.TokenResponse;
import com.bachelor.thesis.organization_education.services.interfaces.JwtService;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.responces.user.RegisteredResponse;
import com.bachelor.thesis.organization_education.repositories.user.TokenRepository;
import com.bachelor.thesis.organization_education.services.interfaces.user.UserCrudService;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.junit.Assert.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TokenServiceImplTest {
    private static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTcwNjM0OTA5OSwiZXhwIjoxNzA2NDM1NDk5fQ.H9s9etukjdCC";
    private static final String HEADER = "Bearer " + TOKEN;
    private static final User USER = User.builder()
            .username("username@gmail.com")
            .password("qwertyuiop[]123!@#")
            .role(Role.SYSTEM_ADMIN)
            .build();

    @InjectMocks
    private TokenServiceImpl serviceMock;
    @Mock
    private JwtService jwtServiceMock;
    @Mock
    private TokenRepository repositoryMock;
    @Mock
    private UserCrudService userServiceMock;

    @Test
    @DisplayName("Checking the createTokenForUser method to throw an exception if the parameters were passed null.")
    void testCreateTokenForUserThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> serviceMock.createTokenForUser(null));
    }

    @Test
    @DisplayName("Test the createTokenForUser method to create a token for the specified user.")
    void testCreateTokenForUserCorrect() {
        Response responseExpected = RegisteredResponse
                .builder()
                .accessToken(TOKEN)
                .username(USER.getUsername())
                .build();

        when(jwtServiceMock.generateToken(USER)).thenReturn(TOKEN);
        when(repositoryMock.save(any(Token.class))).thenReturn(null);

        Response responseActual = serviceMock.createTokenForUser(USER);

        assertThat(responseActual)
                .isNotNull()
                .isEqualTo(responseExpected);
    }

    @Test
    @DisplayName("Check if a JwtException is thrown when checking for token activity for the current time.")
    void testRefreshTokenThrowsJwtExceptionTokenExpired() {
        when(jwtServiceMock.isTokenActive(anyString())).thenThrow(JwtException.class);
        assertThrows(JwtException.class, () -> serviceMock.refreshToken(HEADER));
    }

    @Test
    @DisplayName("Test that a JwtException is thrown when extracting the username from the token header")
    void testRefreshTokenThrowsJwtException() {
        when(jwtServiceMock.extractUsername(anyString())).thenThrow(JwtException.class);
        assertThrows(JwtException.class, () -> serviceMock.refreshToken(HEADER));
    }

    @Test
    @DisplayName("Check if a NotFindEntityInDataBaseException exception occurs when searching for a user in the database.")
    void testRefreshTokenThrowsNotFindEntityInDataBaseException() {
        when(jwtServiceMock.isTokenActive(anyString())).thenReturn(true);
        when(jwtServiceMock.extractUsername(anyString())).thenReturn(USER.getUsername());
        when(userServiceMock.findAuthenticatedUser(any(UserRequest.class))).thenThrow(NotFindEntityInDataBaseException.class);
        assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.refreshToken(HEADER));
    }

    @Test
    @DisplayName("JWT signature update check method.")
    void testRefreshTokenCorrect() {
        String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTcwNjM0OTA5OSwiZXhwIjoxNzA2NDU1MDAwfQ.eVo0Yx9xdrS37uTeG_sew6jZOxHanXdhFSsv3pFksZM";
        TokenResponse responseExpected = TokenResponse
                .builder()
                .accessToken(TOKEN)
                .refreshToken(refreshToken)
                .build();

        when(jwtServiceMock.isTokenActive(anyString())).thenReturn(true);
        when(jwtServiceMock.extractUsername(anyString())).thenReturn(USER.getUsername());
        when(userServiceMock.findAuthenticatedUser(any(UserRequest.class))).thenReturn(USER);
        when(jwtServiceMock.generateToken(any(User.class))).thenReturn(TOKEN);
        when(jwtServiceMock.generateRefreshToken(any(User.class))).thenReturn(refreshToken);
        when(repositoryMock.findAllValidTokenByUser(anyString())).thenReturn(new ArrayList<>());
        when(repositoryMock.save(any(Token.class))).thenReturn(new Token());

        TokenResponse responseActual = serviceMock.refreshToken(HEADER);

        assertThat(responseActual)
                .isNotNull()
                .isEqualTo(responseExpected);
    }

    @Test
    @DisplayName("Check if a NotFindEntityInDataBaseException exception occurs when searching for a JWT signature in the database.")
    void testGetThrowsNotFindEntityInDataBaseException() {
        when(repositoryMock.findByAccessToken(anyString())).thenReturn(Optional.empty());
        assertThrows(NotFindEntityInDataBaseException.class, () -> serviceMock.get(TOKEN));
    }

    @Test
    @DisplayName("Check if a JwtException occurs when the JWT signature is no longer active.")
    void testGetThrowsJwtException_TokenNoActive() {
        Token token = Token.builder()
                .enabled(false)
                .build();

        when(repositoryMock.findByAccessToken(anyString())).thenReturn(Optional.of(token));
        assertThrows(JwtException.class, () -> serviceMock.get(TOKEN));
    }

    @Test
    @DisplayName("Check if a JwtException is thrown when the JWT signature has expired.")
    void testGetThrowsJwtException_TokenExpired() {
        Token token = Token.builder()
                .enabled(true)
                .build();

        when(repositoryMock.findByAccessToken(anyString())).thenReturn(Optional.of(token));
        when(jwtServiceMock.isTokenActive(anyString())).thenReturn(false);

        assertThrows(JwtException.class, () -> serviceMock.get(TOKEN));
    }

    @Test
    @DisplayName("Validate a method that returns a token object with the specified JWT signature.")
    void testGetCorrect() {
        Token tokenExpected = Token.builder()
                .accessToken(TOKEN)
                .tokenType(TokenType.BEARER)
                .enabled(true)
                .build();

        when(repositoryMock.findByAccessToken(anyString())).thenReturn(Optional.of(tokenExpected));
        when(jwtServiceMock.isTokenActive(anyString())).thenReturn(true);

        Token tokenActual = serviceMock.get(TOKEN);

        assertThat(tokenActual)
                .isNotNull()
                .isEqualTo(tokenExpected);
    }
}
