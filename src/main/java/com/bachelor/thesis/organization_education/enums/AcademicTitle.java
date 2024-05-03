package com.bachelor.thesis.organization_education.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration representing academic titles.
 */
@Getter
@RequiredArgsConstructor
public enum AcademicTitle {
    SENIOR_RESEARCHER("Senior researcher", "Старший науковий співробітник"),
    ASSOCIATE_PROFESSOR("Associate professor", "Доцент"),
    PROFESSOR("Professor", "Професор"),
    ASSISTANT("Assistant", "Асистент");

    private final String enName;
    private final String uaName;
}
