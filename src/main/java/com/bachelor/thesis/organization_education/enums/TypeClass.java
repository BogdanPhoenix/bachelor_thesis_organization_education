package com.bachelor.thesis.organization_education.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TypeClass {
    EMPTY("", ""),
    PRACTICAL("Practical", "Практичне"),
    LECTURES("Lectures", "Лекційне");

    private final String enName;
    private final String uaName;
}
