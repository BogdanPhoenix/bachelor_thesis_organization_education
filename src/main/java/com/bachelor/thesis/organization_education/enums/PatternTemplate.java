package com.bachelor.thesis.organization_education.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PatternTemplate {
    TOKEN("^[\\w-]*\\.[\\w-]*\\.[\\w-]*$"),
    EMAIL("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$"),
    PASSWORD("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$&*_])[a-zA-Z\\d!@#$&*_]{8,}$"),
    NAME_USER("^[A-ZА-ЯІЇҐ\\-.\\s]*$");

    private final String value;
}
