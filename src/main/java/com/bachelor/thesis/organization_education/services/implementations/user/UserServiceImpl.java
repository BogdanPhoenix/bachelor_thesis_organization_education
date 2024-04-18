package com.bachelor.thesis.organization_education.services.implementations.user;

import com.bachelor.thesis.organization_education.enums.Role;
import com.bachelor.thesis.organization_education.exceptions.UserCreatingException;
import com.bachelor.thesis.organization_education.requests.find.user.LectureFindRequest;
import com.bachelor.thesis.organization_education.requests.general.user.AuthRequest;
import com.bachelor.thesis.organization_education.requests.insert.abstracts.RegistrationRequest;
import com.bachelor.thesis.organization_education.requests.insert.user.RegistrationLectureRequest;
import com.bachelor.thesis.organization_education.requests.insert.user.RegistrationUserRequest;
import com.bachelor.thesis.organization_education.requests.update.user.UserUpdateRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityService;
import com.bachelor.thesis.organization_education.services.interfaces.user.LectureService;
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
    private final LectureService lectureService;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String jwtIssuerURI;

    @Value("${keycloak.client}")
    private String clientId;

    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public UserRepresentation registration(@NonNull RegistrationRequest request) throws UserCreatingException {
        return registerAccountForAnotherUser(request, Role.UNIVERSITY_ADMIN);
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
    public UserRepresentation registerAccountForAnotherUser(@NonNull RegistrationRequest request, Role role) throws UserCreatingException {
        var userId = "";
        var requestData = (RegistrationUserRequest) request;
        var user = getUserRepresentation(requestData, role);
        var usersResource = getUsersResource();

        try(Response response = usersResource.create(user)) {
            if(!Objects.equals(201, response.getStatus())) {
                var errorMessage = response.readEntity(String.class);
                var message = String.format("The Identity Management server could not successfully create a user on its side. \n" +
                        "It returned an HTTP code: %d. The body of the error: %s.", response.getStatus(), errorMessage);
                throw new UserCreatingException(message);
            }

            var userRepresentation = getUserRepresentation(usersResource, requestData.getUsername());
            userId = userRepresentation.getId();

            if(request instanceof RegistrationLectureRequest) {
                lectureService.registration(request, userId);
            }

            assignRole(userId, role.name());
            emailVerification(userId);

            return userRepresentation;
        }
        catch (Exception e) {
            if(!userId.isBlank()) {
                deleteUserById(userId);
            }

            throw new UserCreatingException(e.getMessage());
        }
    }

    private static @NonNull UserRepresentation getUserRepresentation(RegistrationUserRequest request, Role role) {
        var user = createUser(request);
        var credentialRepresentation = createCredentialRepresentation(request, role);
        var list = new ArrayList<CredentialRepresentation>();

        list.add(credentialRepresentation);
        user.setCredentials(list);

        return user;
    }

    private static @NonNull UserRepresentation createUser(RegistrationUserRequest request) {
        var user = new UserRepresentation();

        user.setEnabled(true);
        user.setUsername(request.getUsername());
        user.setEmail(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmailVerified(false);

        return user;
    }

    private static @NonNull CredentialRepresentation createCredentialRepresentation(RegistrationUserRequest request, Role role) {
        var credentialRepresentation = new CredentialRepresentation();

        credentialRepresentation.setValue(request.getPassword());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setTemporary(role != Role.UNIVERSITY_ADMIN);

        return credentialRepresentation;
    }

    private static @NonNull UserRepresentation getUserRepresentation(UsersResource usersResource, String username) {
        var representationList = usersResource.searchByUsername(username, true);
        return representationList
                .stream()
                .filter(representation -> Objects.equals(false, representation.isEmailVerified()))
                .findFirst()
                .orElseThrow();
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
        lectureService.deleteValue(new LectureFindRequest(UUID.fromString(userId)));
    }

    @Override
    public void deactivateUserById(String userId) {
        universityService.deactivateUserEntity(userId);
        lectureService.disable(new LectureFindRequest(UUID.fromString(userId)));

        updateEnable(userId, false);
    }

    @Override
    public void activate(String userId) {
        lectureService.enable(new LectureFindRequest(UUID.fromString(userId)));
        updateEnable(userId, true);
    }

    private void updateEnable(String userId, boolean value) {
        var users = getUsersResource();
        var representation = users.get(userId).toRepresentation();
        representation.setEnabled(value);
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

    @Override
    public void updateData(@NonNull UserUpdateRequest request, @NonNull String userId) {
        var users = getUsersResource();
        var representation = users.get(userId).toRepresentation();

        if(request.getUsername() != null && !request.getUsername().isBlank()) {
            representation.setUsername(request.getUsername());
            representation.setEmail(request.getUsername());
        }
        if(request.getFirstName() != null && !request.getFirstName().isBlank()) {
            representation.setFirstName(request.getFirstName());
        }
        if(request.getLastName() != null && !request.getLastName().isBlank()) {
            representation.setLastName(request.getLastName());
        }

        users.get(userId).update(representation);
    }

    private UserResource getUserResource(String userId) {
        return getUsersResource()
                .get(userId);
    }
}
