package com.bachelor.thesis.organization_education.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * A list representing different permissions for registered users in the system.
 * Each permission has a specific value.
 */
@Getter
@RequiredArgsConstructor
public enum Permission {
    SYSTEM_ADMIN_READ("admin:read"),
    SYSTEM_ADMIN_UPDATE("admin:update"),
    SYSTEM_ADMIN_CREATE("admin:create"),
    SYSTEM_ADMIN_DELETE("admin:delete"),

    UNIVERSITY_ADMIN_READ("university_admin:read"),
    UNIVERSITY_ADMIN_UPDATE("university_admin:update"),
    UNIVERSITY_ADMIN_CREATE("university_admin:create"),
    UNIVERSITY_ADMIN_DELETE("university_admin:delete"),

    LECTURER_READ("lecturer:read"),
    LECTURER_UPDATE("lecturer:update"),
    LECTURER_CREATE("lecturer:create"),
    LECTURER_DELETE("lecturer:delete"),

    STUDENT_READ("student:read"),
    STUDENT_UPDATE("student:update"),
    STUDENT_CREATE("student:create"),
    STUDENT_DELETE("student:delete");

    private final String value;
}
