package com.bachelor.thesis.organization_education.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration representing academic degrees.
 */
@Getter
@RequiredArgsConstructor
public enum AcademicDegree {
    CANDIDATE_OF_SCIENCES("Candidate of Sciences", "Кандидат наук"),
    DOCTOR_OF_SCIENCES("Doctor of science", "Доктор наук");

    private final String enName;
    private final String uaName;
}
