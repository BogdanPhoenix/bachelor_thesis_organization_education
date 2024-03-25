package com.bachelor.thesis.organization_education.validators;

import com.bachelor.thesis.organization_education.annotations.ValidEmail;
import com.bachelor.thesis.organization_education.enums.PatternTemplate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email.matches(PatternTemplate.EMAIL.getValue());
    }
}
