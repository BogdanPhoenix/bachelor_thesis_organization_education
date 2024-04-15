package com.bachelor.thesis.organization_education.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AcademicDegree {
    EMPTY("", ""),
    CANDIDATE_OF_SCIENCES("Candidate of Sciences", "Кандидат наук"),
    DOCTOR_OF_SCIENCES("Doctor of science", "Доктор наук");

    private final String enName;
    private final String uaName;
}
