package com.bachelor.thesis.organization_education.controllers.user;

import com.bachelor.thesis.organization_education.requests.insert.abstracts.RegistrationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.keycloak.representations.idm.UserRepresentation;
import com.bachelor.thesis.organization_education.enums.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.bachelor.thesis.organization_education.requests.general.user.AuthRequest;
import com.bachelor.thesis.organization_education.services.interfaces.user.UserService;
import com.bachelor.thesis.organization_education.requests.update.user.UserUpdateRequest;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import com.bachelor.thesis.organization_education.requests.insert.user.RegistrationUserRequest;
import com.bachelor.thesis.organization_education.requests.insert.user.RegistrationLecturerRequest;
import com.bachelor.thesis.organization_education.requests.insert.user.RegistrationStudentUserRequest;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @PostMapping("/register-other/student")
    public ResponseEntity<UserRepresentation> registerAccountForStudent(@RequestBody @Valid RegistrationStudentUserRequest registrationRequest) {
        return registration(registrationRequest, Role.STUDENT);
    }

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @PostMapping("/register-other/lecture")
    public ResponseEntity<UserRepresentation> registerAccountForLecture(@RequestBody @Valid RegistrationLecturerRequest registrationRequest) {
        return registration(registrationRequest, Role.LECTURER);
    }

    @PostMapping("/register")
    public ResponseEntity<UserRepresentation> registerUser(@RequestBody @Valid RegistrationUserRequest registrationRequest) {
        return registration(registrationRequest, Role.UNIVERSITY_ADMIN);
    }

    private ResponseEntity<UserRepresentation> registration(
            RegistrationRequest registrationRequest,
            Role role
    ) {
        var response = service.registration(registrationRequest, role);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/auth")
    public ResponseEntity<String> authorization(@RequestBody @Valid AuthRequest authRequest) {
        var response = service.authorization(authRequest);
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping
    public UserRepresentation getUser(Principal principal) {
        return service.getUserById(principal.getName());
    }

    @GetMapping("/{userId}")
    public UserRepresentation getUser(@PathVariable String userId) {
        return service.getUserById(userId);
    }

    @PutMapping("/update-password")
    public void updatePassword(Principal principal) {
        service.updatePassword(principal.getName());
    }

    @PutMapping
    public void updateData(
            @RequestBody @Valid UserUpdateRequest request,
            Principal principal
    ) {
        service.updateData(principal.getName(), request);
    }

    @DeleteMapping
    public void deactivateAccount(Principal principal) {
        service.deactivateUserById(principal.getName());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/activate/{userId}")
    public void active(@PathVariable String userId) {
        service.activate(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable String userId) {
        service.deleteUserById(userId);
    }

    @GetMapping("/account")
    public String redirectToAccountPage(@AuthenticationPrincipal OAuth2AuthenticationToken authToken) {
        if (authToken == null) {
            return "redirect:/";
        }

        OidcUser user = (OidcUser) authToken.getPrincipal();
        return "redirect:" + user.getIssuer() + "/account?referrer=" + user.getIdToken().getAuthorizedParty();
    }
}
