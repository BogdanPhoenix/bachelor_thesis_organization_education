package com.bachelor.thesis.organization_education.services.implementations.user;

import com.bachelor.thesis.organization_education.enums.Role;
import com.bachelor.thesis.organization_education.exceptions.UserCreatingException;
import com.bachelor.thesis.organization_education.requests.general.user.AuthRequest;
import com.bachelor.thesis.organization_education.requests.insert.user.RegistrationOtherUserRequest;
import com.bachelor.thesis.organization_education.requests.insert.user.RegistrationRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityService;
import com.bachelor.thesis.organization_education.services.interfaces.user.UserService;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Keycloak keycloak;
    private final RestTemplate keycloakRestTemplate;
    private final UniversityService universityService;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String jwtIssuerURI;

    @Value("${keycloak.client}")
    private String clientId;

    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public UserRepresentation registration(@NonNull RegistrationRequest request) throws UserCreatingException {
        var reassembledRequest = RegistrationOtherUserRequest.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .matchingPassword(request.getMatchingPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(Role.UNIVERSITY_ADMIN)
                .build();

        return registerAccountForAnotherUser(reassembledRequest, "");
    }

    @Override
    public ResponseEntity<String> authorization(@NonNull AuthRequest authRequest) throws RestClientException {
        var requestBody = new LinkedMultiValueMap<String, String>();
        requestBody.add("grant_type", "password");
        requestBody.add("username", authRequest.getUsername());
        requestBody.add("password", authRequest.getPassword());
        requestBody.add("client_id", clientId);

        return keycloakRestTemplate.postForEntity(
                jwtIssuerURI + "/protocol/openid-connect/token",
                requestBody,
                String.class
        );
    }

    @Override
    public UserRepresentation registerAccountForAnotherUser(@NonNull RegistrationOtherUserRequest request, @NonNull String invitedUserId) throws UserCreatingException {
        var userId = "";
        var user = getUserRepresentation(request);
        var usersResource = getUsersResource();

        try(Response response = usersResource.create(user)) {
            if(!Objects.equals(201, response.getStatus())) {
                throw new UserCreatingException("The Identity Management server could not successfully create a user on its side. It returned an HTTP code " + response.getStatus());
            }

            var representationList = usersResource.searchByUsername(request.getUsername(), true);
            var userRepresentation = representationList
                    .stream()
                    .filter(representation -> Objects.equals(false, representation.isEmailVerified()))
                    .findFirst()
                    .orElseThrow();

            userId = userRepresentation.getId();
            assignRole(userRepresentation.getId(), request.getRole().name());
            emailVerification(userRepresentation.getId());

            return userRepresentation;
        }
        catch (Exception e) {
            if(!userId.isBlank()) {
                deactivateUserById(userId);
            }

            throw new UserCreatingException(e.getMessage());
        }
    }

    private static @NonNull UserRepresentation getUserRepresentation(RegistrationOtherUserRequest request) {
        var user = createUser(request);
        var credentialRepresentation = createCredentialRepresentation(request);
        var list = new ArrayList<CredentialRepresentation>();

        list.add(credentialRepresentation);
        user.setCredentials(list);

        return user;
    }

    private static @NonNull UserRepresentation createUser(@NonNull RegistrationOtherUserRequest request) {
        var user = new UserRepresentation();

        user.setEnabled(true);
        user.setUsername(request.getUsername());
        user.setEmail(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmailVerified(false);

        return user;
    }

    private static @NonNull CredentialRepresentation createCredentialRepresentation(@NonNull RegistrationOtherUserRequest request) {
        var credentialRepresentation = new CredentialRepresentation();

        credentialRepresentation.setValue(request.getPassword());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        return credentialRepresentation;
    }

    private void assignRole(String userId, String roleName) {
        var userResource = getUserResource(userId);
        var rolesResource = getRolesResource();
        var representation = rolesResource
                .get(roleName)
                .toRepresentation();
        userResource.roles()
                .realmLevel()
                .add(Collections.singletonList(representation));
    }

    private RolesResource getRolesResource(){
        return keycloak
                .realm(realm)
                .roles();
    }

    private void emailVerification(String userId) {
        var usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    @Override
    public void deleteUserById(String userId) {
        getUsersResource().delete(userId);
    }

    @Override
    public void deactivateUserById(String userId) {
        universityService.deactivateUserEntity(userId);

        var users = getUsersResource();
        var representation = users.get(userId).toRepresentation();
        representation.setEnabled(false);
        users.get(userId).update(representation);
    }

    @Override
    public UserRepresentation getUserById(String userId) throws NotFoundException {
        return getUsersResource()
                .get(userId)
                .toRepresentation();
    }

    private UsersResource getUsersResource() {
        return keycloak
                .realm(realm)
                .users();
    }

    @Override
    public void updatePassword(String userId) {
        var userResource = getUserResource(userId);
        var actions= new ArrayList<String>();
        actions.add("UPDATE_PASSWORD");
        userResource.executeActionsEmail(actions);
    }

    private UserResource getUserResource(String userId) {
        return getUsersResource()
                .get(userId);
    }
}
