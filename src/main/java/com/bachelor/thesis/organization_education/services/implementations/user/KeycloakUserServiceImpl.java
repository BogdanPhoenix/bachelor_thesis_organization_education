package com.bachelor.thesis.organization_education.services.implementations.user;

import com.bachelor.thesis.organization_education.requests.user.RegistrationRequest;
import com.bachelor.thesis.organization_education.responces.user.UserRegistrationResponse;
import com.bachelor.thesis.organization_education.services.interfaces.user.KeycloakUserService;
import com.bachelor.thesis.organization_education.services.interfaces.user.RoleService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakUserServiceImpl implements KeycloakUserService {
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.client}")
    private String client;
    private final Keycloak keycloak;

    @Override
    public UserRegistrationResponse createUser(RegistrationRequest userRegistration) {
        UserRepresentation user=new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userRegistration.getUsername());
        user.setEmail(userRegistration.getUsername());
        user.setFirstName(userRegistration.getFirstName());
        user.setLastName(userRegistration.getLastName());
        user.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation=new CredentialRepresentation();
        credentialRepresentation.setValue(userRegistration.getPassword());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        List<CredentialRepresentation> list = new ArrayList<>();
        list.add(credentialRepresentation);
        user.setCredentials(list);

        UsersResource usersResource = getUsersResource();

        try(Response response = usersResource.create(user)) {

            if(Objects.equals(201, response.getStatus())){

                List<UserRepresentation> representationList = usersResource
                        .searchByUsername(userRegistration.getUsername(), true);
                if(!CollectionUtils.isEmpty(representationList)){
                    UserRepresentation userRepresentation1 = representationList
                            .stream()
                            .filter(userRepresentation -> Objects.equals(false, userRepresentation.isEmailVerified()))
                            .findFirst()
                            .orElse(null);
                    assert userRepresentation1 != null;
                    emailVerification(userRepresentation1.getId());
                }
                return  UserRegistrationResponse.builder()
                        .username(userRegistration.getUsername())
                        .role(userRegistration.getRole())
                        .firstName(userRegistration.getFirstName())
                        .lastName(userRegistration.getLastName())
                        .build();
            }
        }
        return null;
    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm(realm);
        return realm1.users();
    }

    @Override
    public UserRepresentation getUserById(String userId) {
        return getUsersResource().get(userId).toRepresentation();
    }

    @Override
    public void deleteUserById(String userId) {
        getUsersResource().delete(userId);
    }

    @Override
    public void emailVerification(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    @Override
    public UserResource getUserResource(String userId) {
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    @Override
    public void updatePassword(String userId) {
        UserResource userResource = getUserResource(userId);
        List<String> actions= new ArrayList<>();
        actions.add("UPDATE_PASSWORD");
        userResource.executeActionsEmail(actions);
    }
}
