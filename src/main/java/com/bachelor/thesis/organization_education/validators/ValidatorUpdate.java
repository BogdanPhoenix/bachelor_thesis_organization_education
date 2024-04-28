package com.bachelor.thesis.organization_education.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.bachelor.thesis.organization_education.annotations.ValidNotUpdate;

public class ValidatorUpdate implements ConstraintValidator<ValidNotUpdate, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value == null;
    }
}
