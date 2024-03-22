package com.bachelor.thesis.organization_education.events;

import com.bachelor.thesis.organization_education.responces.user.RegisteredResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private transient RegisteredResponse registeredResponse;
    private String applicationUrl;

    public RegistrationCompleteEvent(RegisteredResponse registeredResponse, String applicationUrl) {
        super(registeredResponse);
        this.registeredResponse = registeredResponse;
        this.applicationUrl = applicationUrl;
    }
}
