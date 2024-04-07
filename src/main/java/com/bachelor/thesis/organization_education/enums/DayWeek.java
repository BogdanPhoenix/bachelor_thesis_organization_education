package com.bachelor.thesis.organization_education.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DayWeek {
    EMPTY("", ""),
    MONDAY("Monday", "Понеділок"),
    TUESDAY("Tuesday", "Вівторок"),
    WEDNESDAY("Wednesday", "Середа"),
    THURSDAY("Thursday", "Четвер"),
    FRIDAY("Friday", "П'ятниця"),
    SATURDAY("Saturday", "Субота");

    private final String enName;
    private final String uaName;
}
