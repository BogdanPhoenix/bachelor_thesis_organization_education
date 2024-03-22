package com.bachelor.thesis.organization_education.controllers.user;

import com.bachelor.thesis.organization_education.events.RegistrationCompleteEvent;
import com.bachelor.thesis.organization_education.requests.user.RegistrationRequest;
import com.bachelor.thesis.organization_education.services.interfaces.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${application.url.main}/register")
public class RegistrationController {
    private final UserService service;
    private final ApplicationEventPublisher publisher;

    @PostMapping
    public String registerUser(
            @RequestBody @Valid RegistrationRequest registrationRequest,
            BindingResult bindingResult,
            final HttpServletRequest request
    ) {
        if(bindingResult.hasErrors()) {
            return "Error";
        }

        var response = service.registration(registrationRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(response, applicationUrl(request)));

        return "Success! Please, check your email for to complete your registration";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        service.verifyEmail(token);
        return "Email verified successfully. Now you can login to your account";
    }

    private String applicationUrl(HttpServletRequest request) {
        return String.format("https://%s:%s%s", request.getServerName(), request.getServerPort(), request.getContextPath());
    }
}
