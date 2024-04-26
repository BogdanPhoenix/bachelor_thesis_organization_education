package com.bachelor.thesis.organization_education.validators;

import com.bachelor.thesis.organization_education.annotations.ValidYear;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class YearValidator implements ConstraintValidator<ValidYear, Short> {
    @Override
    public boolean isValid(Short value, ConstraintValidatorContext context) {
        return value == 0 || value >= 1900;
    }
}
