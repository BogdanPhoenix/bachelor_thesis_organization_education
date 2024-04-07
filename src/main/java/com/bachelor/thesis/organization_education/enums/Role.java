package com.bachelor.thesis.organization_education.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * An enumeration representing the different roles in the system.
 * Each constant represents a specific role that a user can have.
 */

@Getter
@RequiredArgsConstructor
public enum Role {
    EMPTY("", ""),
    ADMIN("Admin", "Адміністратор"),
    UNIVERSITY_ADMIN("University admin", "Адміністратор університету"),
    LECTURER("Lecturer", "Викладач"),
    STUDENT("Student", "Студент");

    private final String enName;
    private final String uaName;
}
