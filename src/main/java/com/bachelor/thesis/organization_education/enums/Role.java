package com.bachelor.thesis.organization_education.enums;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bachelor.thesis.organization_education.enums.Permission.*;

/**
 * An enumeration representing the different roles in the system.
 * Each constant represents a specific role that a user can have.
 */

@Getter
@RequiredArgsConstructor
public enum Role {
    EMPTY("", "", Set.of()),
    ADMIN(
            "Адміністратор системи",
            "System administrator",
            Set.of(SYSTEM_ADMIN_READ, SYSTEM_ADMIN_UPDATE, SYSTEM_ADMIN_CREATE, SYSTEM_ADMIN_DELETE)
    ),
    UNIVERSITY_ADMIN(
            "Адміністратор університету",
            "University administrator",
            Set.of(UNIVERSITY_ADMIN_READ, UNIVERSITY_ADMIN_UPDATE, UNIVERSITY_ADMIN_CREATE, UNIVERSITY_ADMIN_DELETE)
    ),
    LECTURER(
            "Викладач",
            "Lecturer",
            Set.of(LECTURER_READ, LECTURER_UPDATE, LECTURER_CREATE, LECTURER_DELETE)
    ),
    STUDENT(
            "Студент",
            "Student",
            Set.of(STUDENT_READ, STUDENT_UPDATE, STUDENT_CREATE, STUDENT_DELETE)
    );

    private final String uaName;
    private final String enName;
    private final Set<Permission> permissions;

    public @NonNull List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
