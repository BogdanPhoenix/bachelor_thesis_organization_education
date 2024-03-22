package com.bachelor.thesis.organization_education.services.interfaces.user;

import lombok.NonNull;
import io.jsonwebtoken.JwtException;
import com.bachelor.thesis.organization_education.responces.user.TokenResponse;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.user.RegistrationRequest;
import com.bachelor.thesis.organization_education.responces.user.RegisteredResponse;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

public interface UserService {
    /**
     * Handles the registration process for a new user based on the provided registration request.
     *
     * @param request the registration request containing user information.
     * @return JWT token and registered user data.
     * @throws DuplicateException if the user already exists in the system.
     * @throws NullPointerException if null was passed to the request.
     */
    RegisteredResponse registration(@NonNull RegistrationRequest request) throws DuplicateException, NullPointerException;

    /**
     * Updates the authentication token for the user based on the existing JWT signature.
     *
     * @param token a JWT token that identifies the user and gives permission to interact with his or her data.
     * @throws JwtException if the JWT signature does not match the locally computed signature or the JWT signature has expired.
     * @throws IllegalArgumentException if the JWT string is null or empty or only whitespace.
     * @throws NotFindEntityInDataBaseException if the user by the specified JWT signature could not be found.
     */
    TokenResponse refreshToken(@NonNull String token) throws JwtException, IllegalArgumentException, NotFindEntityInDataBaseException;

    /**
     * Verifies user confirmation using the JWT signature that was sent to the email.
     *
     * @param token a JWT token that identifies the user and authorizes the activation of user data.
     * @throws NotFindEntityInDataBaseException if the JWT signature was not found in the database or if the user was not found in the database.
     * @throws JwtException if the JWT signature has expired or when the JWT signature is no longer active.
     */
    void verifyEmail(@NonNull String token) throws NotFindEntityInDataBaseException, JwtException;
}
