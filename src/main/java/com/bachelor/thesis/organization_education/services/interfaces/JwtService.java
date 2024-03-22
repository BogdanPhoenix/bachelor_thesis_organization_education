package com.bachelor.thesis.organization_education.services.interfaces;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    /**
     * Extracts the username from a given JWT (JSON Web Token).
     *
     * @param token the JWT from which to extract the username.
     * @return the extracted username.
     * @throws JwtException if the JWT signature does not match the locally computed signature.
     * @throws IllegalArgumentException if the JWT string is null or empty or only whitespace.
     */
    String extractUsername(String token) throws JwtException, IllegalArgumentException;

    /**
     * Generic method to extract a specific claim from a JWT using a provided claims resolver function.
     *
     * @param <T>            the type of the claim to be extracted.
     * @param token          the JWT from which to extract the claim.
     * @param claimsResolver the function to resolve the desired claim from the JWT's claims set.
     * @return the extracted claim of type T.
     * @throws JwtException if parsing, signature verification, or JWT validation fails.
     * @throws IllegalArgumentException if the JWT string is null or empty or only whitespace.
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws JwtException, IllegalArgumentException;

    /**
     * Generates a JWT (JSON Web Token) based on the provided UserDetails.
     * This method uses default claims and expiration time for the token.
     *
     * @param userDetails the UserDetails containing information about the user.
     * @return the generated JWT.
     * @throws NullPointerException if the user data contains the value null.
     */
    String generateToken(UserDetails userDetails) throws NullPointerException;

    /**
     * Generates a JWT (JSON Web Token) with additional claims based on the provided UserDetails.
     *
     * @param extraClaims   additional claims to be included in the JWT.
     * @param userDetails   the UserDetails containing information about the user.
     * @return the generated JWT.
     * @throws NullPointerException if the user data contains the value null.
     */
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) throws NullPointerException;

    /**
     * Generates a refresh token based on the provided UserDetails.
     * This method uses default claims and expiration time for the refresh token.
     *
     * @param userDetails the UserDetails containing information about the user.
     * @return the generated refresh token.
     * @throws NullPointerException if the user data contains the value null.
     */
    String generateRefreshToken(UserDetails userDetails) throws NullPointerException;

    /**
     * Validates the provided JWT (JSON Web Token) against the given UserDetails.
     *
     * @param token       the JWT to be validated.
     * @param userDetails the UserDetails of the user for validation.
     * @return true if the token is valid for the provided user, false otherwise.
     * @throws JwtException if parsing, signature verification, or JWT validation fails.
     * @throws IllegalArgumentException if the JWT string is null or empty or only whitespace.
     */
    boolean isTokenValid(String token, UserDetails userDetails) throws JwtException, IllegalArgumentException;

    /**
     * Checks whether the provided JWT (JSON Web Token) token has not expired.
     * @param accessToken the JWT to be validated.
     * @return true if the token has not expired, false otherwise.
     * @throws JwtException if it was not possible to obtain data on the token validity period.
     * @throws IllegalArgumentException if the jwt string is null or empty or only whitespace
     */
    boolean isTokenActive(String accessToken) throws JwtException, IllegalArgumentException;
}