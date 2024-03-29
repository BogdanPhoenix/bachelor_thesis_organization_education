package com.bachelor.thesis.organization_education.services.implementations.user;

import com.bachelor.thesis.organization_education.services.interfaces.user.KeycloakUserService;
import com.bachelor.thesis.organization_education.services.interfaces.user.RoleService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    @Value("${keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;

    private final KeycloakUserService keycloakUserService;

    @Override
    public void assignRole(String userId, String roleName) {
        UserResource userResource = keycloakUserService.getUserResource(userId);
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(representation));
    }

    private RolesResource getRolesResource(){
        return keycloak.realm(realm).roles();
    }
}

