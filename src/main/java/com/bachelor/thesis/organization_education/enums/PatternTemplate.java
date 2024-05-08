package com.bachelor.thesis.organization_education.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum defining various pattern templates for validation.
 * Each pattern template corresponds to a specific type of data validation.
 */
@Getter
@RequiredArgsConstructor
public enum PatternTemplate {
    TOKEN("^[\\w-]*\\.[\\w-]*\\.[\\w-]*$"),
    EMAIL("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"),
    PASSWORD("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$&*_])[a-zA-Z\\d!@#$&*_]{8,}$"),
    USER_FIRST_AND_LAST_NAME("^[A-ZА-ЯІЇЄҐ\\-.\\s']*$"),
    STRICT_SET_ALLOWED_CHARS("^[A-ZА-ЯІЇЄҐ\\-.\\s']*$"),
    SET_ALLOWED_CHARS("^[0-9A-ZА-ЯІЇЄҐ\\-.\\s'№]*$");

    private final String value;
}
