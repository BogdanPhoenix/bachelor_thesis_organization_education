package com.bachelor.thesis.organization_education.services.interfaces.user;

import lombok.NonNull;
import jakarta.ws.rs.NotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.keycloak.representations.idm.UserRepresentation;
import com.bachelor.thesis.organization_education.enums.Role;
import com.bachelor.thesis.organization_education.exceptions.UserCreatingException;
import com.bachelor.thesis.organization_education.requests.general.user.AuthRequest;
import com.bachelor.thesis.organization_education.requests.update.user.UserUpdateRequest;
import com.bachelor.thesis.organization_education.requests.insert.abstracts.RegistrationRequest;

@Transactional
public interface UserService {
    /**
     * Handles the registration process for a new user based on the provided registration request.
     *
     * @param request the registration request containing user information.
     * @param role the value of the user's role.
     * @return user data.
     * @throws UserCreatingException if during registration it was not possible to create a user account.
     */
    UserRepresentation registration(@NonNull RegistrationRequest request, Role role) throws UserCreatingException;

    /**
     * Manages the login process based on the provided authorization request.
     *
     * @param authRequest user data for authorization.
     * @return JSON object with confirmation tokens.
     * @throws OAuth2AuthenticationException if you failed to log in to the server.
     */
    ResponseEntity<String> authorization(@NonNull AuthRequest authRequest) throws OAuth2AuthenticationException;

    /**
     * Method for returning information about a user by their UUID.
     *
     * @param userId the user's UUID on the server.
     * @return user data.
     * @throws NotFoundException if the user could not be found by the specified ID.
     */
    UserRepresentation getUserById(String userId) throws NotFoundException;

    /**
     * Method for deleting a user account from the system by its UUID.
     *
     * @param userId the user's UUID on the server.
     */
    void deleteUserById(@NotBlank String userId);

    /**
     * A method for deactivating a user account from the system by its UUID.
     *
     * @param userId the user's UUID on the server.
     */
    void deactivateUserById(String userId);

    /**
     * Method of activating a user account from the system by its UUID.
     *
     * @param userId UUID of the user on the server.
     */
    void activate(@NotBlank String userId);

    /**
     * Method for sending a request to update user data.
     *
     * @param userId the user's UUID on the server.
     */
    void updatePassword(String userId);

    /**
     * A method of updating the user's personal data.
     *
     * @param userId the user's UUID on the server.
     * @param request data that needs to be updated.
     */
    void updateData(@NonNull String userId, @NonNull UserUpdateRequest request);
}
