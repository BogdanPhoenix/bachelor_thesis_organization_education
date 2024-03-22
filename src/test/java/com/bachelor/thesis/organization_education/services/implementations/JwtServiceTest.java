package com.bachelor.thesis.organization_education.services.implementations;

import java.util.Map;
import lombok.NonNull;
import java.util.HashMap;
import io.jsonwebtoken.Claims;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import com.bachelor.thesis.organization_education.services.interfaces.JwtService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doReturn;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JwtServiceTest {
    private static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTcwNjM0OTA5OSwiZXhwIjoxNzA2NDM1NDk5fQ.H9s9etukjdCC";
    private static final String EXPECTED_USERNAME = "username";
    private static final UserDetails USER_DETAILS = User
            .withUsername(EXPECTED_USERNAME)
            .password("password")
            .roles("USER")
            .build();

    @Autowired
    private JwtService jwtService;

    @Test
    @DisplayName("Checking for the correct extraction of the username from the generated token.")
    void testExtractUsernameCorrect() {
        var token = jwtService.generateToken(USER_DETAILS);
        var actualUsername = jwtService.extractUsername(token);

        assertEquals(EXPECTED_USERNAME, actualUsername);
    }

    @Test
    @DisplayName("Checks for an exception when the JWT signature does not match the locally computed signature.")
    void testExtractUsernameThrowsJwtException() {
        assertThrows(JwtException.class,
                () -> jwtService.extractUsername(TOKEN)
        );
    }

    @ParameterizedTest
    @MethodSource("testEmptyTokens")
    @DisplayName("Checks for an exception when the JWT signature contains a null value that is passed to the extractUsername method.")
    void testExtractUsernameThrowsIllegalArgumentException(String token) {
        assertThrows(IllegalArgumentException.class,
                () -> jwtService.extractUsername(token)
        );
    }

    @Test
    @DisplayName("Checking the method to extract specific requirements from a given JWT.")
    void testExtractClaimCorrect() {
        var expected = USER_DETAILS.getUsername();
        var token = jwtService.generateToken(USER_DETAILS);
        var mockClaims = mock(Claims.class);

        doReturn(expected)
                .when(mockClaims)
                .getSubject();

        var actualClaim = jwtService.extractClaim(token, Claims::getSubject);
        assertEquals(expected, actualClaim);
    }

    @Test
    @DisplayName("Checks for an exception when the JWT signature does not match the locally calculated signature in the extractClaim method.")
    void testExtractClaimThrowsJwtException() {
        assertThrows(JwtException.class,
                () -> jwtService.extractClaim(TOKEN, Claims::getSubject)
        );
    }

    @ParameterizedTest
    @MethodSource("testEmptyTokens")
    @DisplayName("Checks for an exception when the JWT signature contains a null value that is passed to the extractClaim method.")
    void testExtractClaimThrowsIllegalArgumentException(String token) {
        assertThrows(IllegalArgumentException.class,
                () -> jwtService.extractClaim(token, Claims::getSubject)
        );
    }

    @Test
    @DisplayName("Checking the generation of a token for work.")
    void testGenerateTokenWithUserDetailsCorrect() {
        var token = jwtService.generateToken(USER_DETAILS);

        assertThat(token)
                .isNotNull()
                .isNotBlank();
    }

    @Test
    @DisplayName("Checking for a NullPointerException when generating a token when null was passed in the parameters.")
    void testGenerateTokenWithUserDetailsThrowNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> jwtService.generateToken(null)
        );
    }

    @Test
    @DisplayName("Checking JWT token generation when a user has multiple ")
    void testGenerateTokenWithExtractClaimsAndUserDetailsCorrect() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", "ADMIN");

        var token = jwtService.generateToken(extraClaims, USER_DETAILS);

        assertThat(token)
                .isNotNull()
                .isNotBlank();
    }

    @Test
    @DisplayName("Checking the generation of a refresh token for work.")
    void testGenerateRefreshTokenCorrect() {
        var token = jwtService.generateRefreshToken(USER_DETAILS);

        assertThat(token)
                .isNotNull()
                .isNotBlank();
    }

    @Test
    @DisplayName("Checking for a NullPointerException when generating a refresh token when null was passed in the parameters.")
    void testGenerateRefreshTokenCorrectThrowNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> jwtService.generateRefreshToken(null)
        );
    }

    @Test
    @DisplayName("Verify that the token matches the user's data.")
    void testIsTokenValidCorrect() {
        var token = jwtService.generateToken(USER_DETAILS);
        assertTrue(jwtService.isTokenValid(token, USER_DETAILS));
    }

    @Test
    @DisplayName("Checks for an exception when the JWT signature does not match the locally calculated signature in the isTokenValid method.")
    void testIsTokenValidThrowsJwtException() {
        assertThrows(JwtException.class,
                () -> jwtService.isTokenValid(TOKEN, USER_DETAILS)
        );
    }

    @ParameterizedTest
    @MethodSource("testEmptyTokens")
    @DisplayName("Checks for an exception when the JWT signature contains a null value that is passed to the isTokenValid method.")
    void testIsTokenValidThrowsIllegalArgumentException(String token) {
        assertThrows(IllegalArgumentException.class,
                () -> jwtService.isTokenValid(token, USER_DETAILS)
        );
    }

    @ParameterizedTest
    @MethodSource("testEmptyTokens")
    @DisplayName("Checks for an exception when the JWT signature contains a null value that is passed to the isTokenActive method.")
    void testIsActiveTokenThrowsIllegalArgumentException(String token) {
        assertThrows(IllegalArgumentException.class,
                () -> jwtService.isTokenActive(token)
        );
    }

    private static @NonNull Stream<Arguments> testEmptyTokens() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(""),
                Arguments.of("         ")
        );
    }

    @Test
    @DisplayName("Check method that checks whether the token has not expired.")
    void testIsActiveTokenCorrect() {
        var token = jwtService.generateToken(USER_DETAILS);
        assertTrue(jwtService.isTokenActive(token));
    }

    @Test
    @DisplayName("Checks for an exception when an invalid token is passed to the isActiveToken method.")
    void testIsActiveTokenThrowsJwtException() {
        var incorrectToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTcwNyM0OTA5OSwiZ5hwdj5xNz54NDM1NDk5fQ.H9s9etukjdCC";
        assertThrows(JwtException.class,
                () -> jwtService.isTokenActive(incorrectToken)
        );
    }
}