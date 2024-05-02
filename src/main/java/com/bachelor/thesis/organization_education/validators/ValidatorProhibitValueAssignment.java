package com.bachelor.thesis.organization_education.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.bachelor.thesis.organization_education.annotations.ProhibitValueAssignment;

public class ValidatorProhibitValueAssignment implements ConstraintValidator<ProhibitValueAssignment, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value == null;
    }
}
