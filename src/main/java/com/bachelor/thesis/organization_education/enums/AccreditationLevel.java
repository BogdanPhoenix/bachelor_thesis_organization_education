package com.bachelor.thesis.organization_education.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Levels of accreditation of educational institutions.
 */
@Getter
@RequiredArgsConstructor
public enum AccreditationLevel {
    FIRST("1st", "1-й"),
    SECOND("2nd", "2-й"),
    THIRD("3rd", "3-й"),
    FOURTH("4th", "4-й");

    private final String enName;
    private final String uaName;
}
