package com.bachelor.thesis.organization_education.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Levels of accreditation of educational institutions.
 */
@Getter
@RequiredArgsConstructor
public enum AccreditationLevel {
    EMPTY("", ""),
    FIRST("First", "Перший"),
    SECOND("Second", "Другий"),
    THIRD("Third", "Третій"),
    FOURTH("Fourth", "Четвертий");

    private final String enName;
    private final String uaName;
}
