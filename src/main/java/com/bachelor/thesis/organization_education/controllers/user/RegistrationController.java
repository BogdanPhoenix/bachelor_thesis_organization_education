package com.bachelor.thesis.organization_education.controllers.user;

import com.bachelor.thesis.organization_education.events.RegistrationCompleteEvent;
import com.bachelor.thesis.organization_education.requests.user.RegistrationRequest;
import com.bachelor.thesis.organization_education.services.interfaces.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {
    private final UserService service;
    private final ApplicationEventPublisher publisher;

    @PostMapping
    public ResponseEntity<String> registerUser(
            @RequestBody @Valid RegistrationRequest registrationRequest,
            BindingResult bindingResult,
            final HttpServletRequest request
    ) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        var response = service.registration(registrationRequest);
        var requestURL = request.getRequestURL().toString();
        publisher.publishEvent(new RegistrationCompleteEvent(response, requestURL));

        return ResponseEntity.ok("Success! Please, check your email for to complete your registration");
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        service.verifyEmail(token);
        return ResponseEntity.ok("Email verified successfully. Now you can login to your account");
    }
}
