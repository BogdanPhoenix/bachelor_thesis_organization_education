package com.bachelor.thesis.organization_education.services.implementations.user;

import com.bachelor.thesis.organization_education.requests.user.RegistrationRequest;
import com.bachelor.thesis.organization_education.responces.user.UserRegistrationResponse;
import com.bachelor.thesis.organization_education.services.interfaces.user.UserService;
import jakarta.ws.rs.core.Response;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public UserRegistrationResponse registration(@NonNull RegistrationRequest request) {
        var user = getUserRepresentation(request);
        var usersResource = getUsersResource();

        try(Response response = usersResource.create(user)) {
            if(!Objects.equals(201, response.getStatus())) {
                return UserRegistrationResponse.empty();
            }

            var representationList = usersResource.searchByUsername(request.getUsername(), true);

            if(!CollectionUtils.isEmpty(representationList)){
                UserRepresentation userRepresentation = representationList
                        .stream()
                        .filter(representation -> Objects.equals(false, representation.isEmailVerified()))
                        .findFirst()
                        .orElseThrow();

                assignRole(userRepresentation.getId(), request.getRole().name());
                emailVerification(userRepresentation.getId());
            }

            return UserRegistrationResponse.builder()
                    .username(request.getUsername())
                    .role(request.getRole())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .build();
        }
    }

    @Override
    public UserRepresentation getUserById(String userId) {
        return getUsersResource().get(userId).toRepresentation();
    }

    @Override
    public void deleteUserById(String userId) {
        getUsersResource().delete(userId);
    }

    private static @NonNull UserRepresentation getUserRepresentation(RegistrationRequest request) {
        var user = createUser(request);
        var credentialRepresentation = createCredentialRepresentation(request);

        List<CredentialRepresentation> list = new ArrayList<>();
        list.add(credentialRepresentation);
        user.setCredentials(list);

        return user;
    }

    private static @NonNull UserRepresentation createUser(RegistrationRequest request) {
        var user = new UserRepresentation();

        user.setEnabled(true);
        user.setUsername(request.getUsername());
        user.setEmail(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmailVerified(false);

        return user;
    }

    private static @NonNull CredentialRepresentation createCredentialRepresentation(RegistrationRequest request) {
        var credentialRepresentation = new CredentialRepresentation();

        credentialRepresentation.setValue(request.getPassword());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        return credentialRepresentation;
    }

    private UsersResource getUsersResource() {
        return keycloak
                .realm(realm)
                .users();
    }

    private void emailVerification(String userId) {
        var usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    private void assignRole(String userId, String roleName) {
        UserResource userResource = getUserResource(userId);
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(representation));
    }

    private UserResource getUserResource(String userId) {
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    private RolesResource getRolesResource(){
        return keycloak.realm(realm)
                .roles();
    }

    @Override
    public void updatePassword(String userId) {
        UserResource userResource = getUserResource(userId);
        List<String> actions= new ArrayList<>();
        actions.add("UPDATE_PASSWORD");
        userResource.executeActionsEmail(actions);
    }
}
