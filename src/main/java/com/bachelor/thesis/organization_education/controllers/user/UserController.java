package com.bachelor.thesis.organization_education.controllers.user;

import com.bachelor.thesis.organization_education.requests.user.RegistrationRequest;
import com.bachelor.thesis.organization_education.responces.user.UserRegistrationResponse;
import com.bachelor.thesis.organization_education.services.interfaces.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @PostMapping("/register")
    public UserRegistrationResponse registerUser(
            @RequestBody @Valid RegistrationRequest registrationRequest,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            return UserRegistrationResponse.empty();
        }

        return service.registration(registrationRequest);
    }

    @GetMapping
    public UserRepresentation getUser(Principal principal) {
        return service.getUserById(principal.getName());
    }

    @PutMapping("/update-password")
    public void updatePassword(Principal principal) {
        service.updatePassword(principal.getName());
    }

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
