package com.bachelor.thesis.organization_education.controllers.user;

import com.bachelor.thesis.organization_education.requests.general.user.AuthRequest;
import com.bachelor.thesis.organization_education.requests.insert.user.RegistrationOtherUserRequest;
import com.bachelor.thesis.organization_education.requests.insert.user.RegistrationRequest;
import com.bachelor.thesis.organization_education.services.interfaces.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<UserRepresentation> registerUser(
            @RequestBody @Valid RegistrationRequest registrationRequest,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(new UserRepresentation());
        }

        var response = service.registration(registrationRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/auth")
    public ResponseEntity<String> authorization(
            @RequestBody @Valid AuthRequest authRequest,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("The data you provided contains errors.");
        }

        var response = service.authorization(authRequest);

        if (!Objects.equals(HttpStatus.OK, response.getStatusCode())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
        return ResponseEntity.ok(response.getBody());
    }

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @PostMapping("/register-other")
    public ResponseEntity<UserRepresentation> registerAccountForAnotherUser(
            @RequestBody @Valid RegistrationOtherUserRequest registrationRequest,
            Principal principal,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(new UserRepresentation());
        }

        var response = service.registerAccountForAnotherUser(registrationRequest, principal.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
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

    @DeleteMapping
    public void deactivateAccount(Principal principal) {
        service.deactivateUserById(principal.getName());
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
