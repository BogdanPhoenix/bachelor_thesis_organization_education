package com.bachelor.thesis.organization_education.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.bachelor.thesis.organization_education.annotations.ValidYear;

public class YearValidator implements ConstraintValidator<ValidYear, Short> {
    @Override
    public boolean isValid(Short value, ConstraintValidatorContext context) {
        return value == null || value >= 1900;
    }
}
