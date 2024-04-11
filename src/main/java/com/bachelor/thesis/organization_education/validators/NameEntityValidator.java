package com.bachelor.thesis.organization_education.validators;

import com.bachelor.thesis.organization_education.annotations.ValidNameEntity;
import com.bachelor.thesis.organization_education.enums.PatternTemplate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameEntityValidator implements ConstraintValidator<ValidNameEntity, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.toUpperCase()
                .matches(PatternTemplate.STRING_VALUE.getValue());
    }
}
