package com.bachelor.thesis.organization_education.controllers.user;

import com.bachelor.thesis.organization_education.requests.user.RegistrationRequest;
import com.bachelor.thesis.organization_education.responces.user.UserRegistrationResponse;
import com.bachelor.thesis.organization_education.services.interfaces.user.KeycloakUserService;
import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class KeycloakUserApi {
    private final KeycloakUserService keycloakUserService;

    @PostMapping
    public UserRegistrationResponse createUser(@RequestBody RegistrationRequest userRegistrationRecord) {
        return keycloakUserService.createUser(userRegistrationRecord);
    }

    @GetMapping
    public UserRepresentation getUser(Principal principal) {
        return keycloakUserService.getUserById(principal.getName());
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable String userId) {
        keycloakUserService.deleteUserById(userId);
    }

    @PutMapping("/{userId}/send-verify-email")
    public void sendVerificationEmail(@PathVariable String userId) {
        keycloakUserService.emailVerification(userId);
    }

    @PutMapping("/update-password")
    public void updatePassword(Principal principal) {
        keycloakUserService.updatePassword(principal.getName());
    }
}