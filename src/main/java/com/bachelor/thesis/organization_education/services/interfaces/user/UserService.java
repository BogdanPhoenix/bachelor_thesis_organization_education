package com.bachelor.thesis.organization_education.services.interfaces.user;

import com.bachelor.thesis.organization_education.responces.user.UserRegistrationResponse;
import lombok.NonNull;
import com.bachelor.thesis.organization_education.requests.user.RegistrationRequest;
import org.keycloak.representations.idm.UserRepresentation;

public interface UserService {
    /**
     * Handles the registration process for a new user based on the provided registration request.
     *
     * @param request the registration request containing user information.
     * @return registered user data.
     */
    UserRegistrationResponse registration(@NonNull RegistrationRequest request);

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
