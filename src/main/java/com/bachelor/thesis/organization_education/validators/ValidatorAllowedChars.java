package com.bachelor.thesis.organization_education.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.bachelor.thesis.organization_education.enums.PatternTemplate;
import com.bachelor.thesis.organization_education.annotations.ValidAllowedChars;

public class ValidatorAllowedChars implements ConstraintValidator<ValidAllowedChars, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || value.toUpperCase()
                .matches(PatternTemplate.SET_ALLOWED_CHARS.getValue());
    }
}
