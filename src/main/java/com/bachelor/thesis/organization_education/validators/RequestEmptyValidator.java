package com.bachelor.thesis.organization_education.validators;

import com.bachelor.thesis.organization_education.annotations.ValidRequestEmpty;
import com.bachelor.thesis.organization_education.requests.general.abstracts.Request;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RequestEmptyValidator implements ConstraintValidator<ValidRequestEmpty, Request> {
    @Override
    public boolean isValid(Request request, ConstraintValidatorContext constraintValidatorContext) {
        return !request.isEmpty();
    }
}
