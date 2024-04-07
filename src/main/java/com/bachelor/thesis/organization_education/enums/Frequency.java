package com.bachelor.thesis.organization_education.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Frequency {
    EMPTY("", ""),
    WEEKLY("Weekly", "Щотижня"),
    NUMERATOR("Numerator", "Чисельник"),
    DENOMINATOR("Denominator", "Знаменник");

    private final String enName;
    private final String uaName;
}
