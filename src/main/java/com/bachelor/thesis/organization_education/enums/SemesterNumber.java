package com.bachelor.thesis.organization_education.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration representing semester numbers with their English and Ukrainian names.
 */
@Getter
@RequiredArgsConstructor
public enum SemesterNumber {
    FIRST("1st", "1-й"),
    SECOND("2nd", "2-й"),
    THIRD("3rd", "3-й"),
    FOURTH("4th", "4-й"),
    FIFTH("5th", "5-й"),
    SIXTH("6th", "6-й"),
    SEVENTH("7th", "7-й"),
    EIGHTH("8th", "8-й"),
    NINTH("9th", "9-й"),
    TENTH("10th", "10-й");

    private final String enName;
    private final String uaName;
}
