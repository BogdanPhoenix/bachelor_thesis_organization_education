package com.bachelor.thesis.organization_education.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * An enumeration representing different types of classes.
 */
@Getter
@RequiredArgsConstructor
public enum TypeClass {
    PRACTICAL("Practical", "Практичне"),
    LECTURES("Lectures", "Лекційне");

    private final String enName;
    private final String uaName;
}
