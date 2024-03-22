package com.bachelor.thesis.organization_education.services.interfaces.user;

import lombok.NonNull;
import io.jsonwebtoken.JwtException;
import org.springframework.transaction.annotation.Transactional;
import com.bachelor.thesis.organization_education.dto.user.Token;
import org.springframework.security.core.userdetails.UserDetails;
import com.bachelor.thesis.organization_education.responces.user.TokenResponse;
import com.bachelor.thesis.organization_education.responces.user.RegisteredResponse;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

@Transactional
public interface TokenService {
    /**
     * Creating a token for the specified user.
     * @param user the user for whom you want to create a token.
     * @return a response containing the user's token and the user's data.
     * @throws NullPointerException if null was passed to the method parameters.
     */
    RegisteredResponse createTokenForUser(@NonNull UserDetails user) throws NullPointerException;

    /**
     * Generating a new refresh token for the user
     * @param token a refresh token that contains user data.
     * @return token object for verifying and updating identity.
     * @throws JwtException if the JWT signature does not match the locally computed signature or the JWT signature has expired.
     * @throws IllegalArgumentException if the JWT string is null or empty or only whitespace.
     * @throws NotFindEntityInDataBaseException if the user by the specified JWT signature could not be found.
     */
    TokenResponse refreshToken(@NonNull String token) throws JwtException, IllegalArgumentException, NotFindEntityInDataBaseException;

    /**
     * Marks the user's token as inactive.
     * @param accessToken JWT signature.
     * @throws NotFindEntityInDataBaseException if the JWT signature could not be found in the database.
     * @throws JwtException if the JWT signature has expired or when the JWT signature is no longer active.
     */
    void revokeUserToken(@NonNull String accessToken) throws NotFindEntityInDataBaseException;

    /**
     * Search token object in the database by JWT signature.
     * @param accessToken JWT signature.
     * @return a token object with the specified JWT signature.
     * @throws NotFindEntityInDataBaseException if the JWT signature could not be found in the database.
     * @throws JwtException if the JWT signature has expired or when the JWT signature is no longer active.
     */
    Token get(@NonNull String accessToken) throws NotFindEntityInDataBaseException, JwtException;
}
