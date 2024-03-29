package com.bachelor.thesis.organization_education.services.interfaces.user;

import com.bachelor.thesis.organization_education.requests.user.RegistrationRequest;
import com.bachelor.thesis.organization_education.responces.user.UserRegistrationResponse;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakUserService {

    UserRegistrationResponse createUser(RegistrationRequest userRegistrationRecord);
    UserRepresentation getUserById(String userId);
    void deleteUserById(String userId);
    void emailVerification(String userId);
    UserResource getUserResource(String userId);
    void updatePassword(String userId);
}