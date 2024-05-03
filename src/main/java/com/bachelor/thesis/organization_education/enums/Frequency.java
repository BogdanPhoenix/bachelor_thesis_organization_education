package com.bachelor.thesis.organization_education.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * An enumeration representing the frequency of the classes.
 */
@Getter
@RequiredArgsConstructor
public enum Frequency {
    WEEKLY("Weekly", "Щотижня"),
    NUMERATOR("Numerator", "Чисельник"),
    DENOMINATOR("Denominator", "Знаменник");

    private final String enName;
    private final String uaName;
}
