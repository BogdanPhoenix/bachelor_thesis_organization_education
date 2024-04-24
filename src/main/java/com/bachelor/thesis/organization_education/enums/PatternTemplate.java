package com.bachelor.thesis.organization_education.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PatternTemplate {
    TOKEN("^[\\w-]*\\.[\\w-]*\\.[\\w-]*$"),
    EMAIL("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$"),
    PASSWORD("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$&*_])[a-zA-Z\\d!@#$&*_]{8,}$"),
    USER_FIRST_AND_LAST_NAME("^[A-ZА-ЯІЇҐ\\-.\\s']*$"),
    STRING_VALUE("^[A-ZА-ЯІЇҐ\\-.\\s']*$"),
    EMPTY_UUID("00000000-0000-0000-0000-000000000000");

    private final String value;
}
