package com.bachelor.thesis.organization_education.services.interfaces.user;

import com.bachelor.thesis.organization_education.exceptions.UserCreatingException;
import com.bachelor.thesis.organization_education.requests.user.AuthRequest;
import com.bachelor.thesis.organization_education.requests.user.RegistrationOtherUserRequest;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import com.bachelor.thesis.organization_education.requests.user.RegistrationRequest;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;

@Transactional
public interface UserService {
    /**
     * Handles the registration process for a new user based on the provided registration request.
     *
     * @param request the registration request containing user information.
     * @return registered user data.
     * @throws UserCreatingException if during registration it was not possible to create a user account.
     */
    UserRepresentation registration(@NonNull RegistrationRequest request) throws UserCreatingException;

    /**
     * Manages the login process based on the provided authorization request.
     *
     * @param authRequest user data for authorization.
     * @return JSON object with confirmation tokens.
     */
    ResponseEntity<String> authorization(@NonNull AuthRequest authRequest);

    /**
     * Manages the user registration process by the university administrator based on the registration request provided.
     *
     * @param request the registration request containing user information.
     * @param inviteUserId university administrator UUID.
     * @return user data.
     * @throws UserCreatingException if during registration it was not possible to create a user account.
     */
    UserRepresentation registerAccountForAnotherUser(@NonNull RegistrationOtherUserRequest request, @NonNull String inviteUserId) throws UserCreatingException;

    /**
     * Method for returning information about a user by their UUID.
     *
     * @param userId the user's UUID on the server.
     * @return user data.
     */
    UserRepresentation getUserById(String userId);

    /**
     * Method for deleting a user account from the system by its UUID.
     *
     * @param userId the user's UUID on the server.
     */
    void deleteUserById(String userId);

    /**
     * Method for sending a request to update user data.
     *
     * @param userId the user's UUID on the server.
     */
    void updatePassword(String userId);
}
