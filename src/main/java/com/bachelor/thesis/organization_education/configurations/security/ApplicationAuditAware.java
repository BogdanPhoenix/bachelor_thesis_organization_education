package com.bachelor.thesis.organization_education.configurations.security;

import com.bachelor.thesis.organization_education.dto.user.User;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Long> {
    @Override
    public @NonNull Optional<Long> getCurrentAuditor() {
        var authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if(isNotAuthenticated(authentication)) {
            return Optional.empty();
        }

        var registeredUser = (User) authentication.getPrincipal();

        return Optional.of(registeredUser.getId());
    }

    private boolean isNotAuthenticated(Authentication authentication) {
        return authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken;
    }
}
