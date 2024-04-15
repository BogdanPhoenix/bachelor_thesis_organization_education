package com.bachelor.thesis.organization_education.validators;

import com.bachelor.thesis.organization_education.annotations.PasswordMatches;
import com.bachelor.thesis.organization_education.requests.general.abstracts.PasswordRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, PasswordRequest> {
    @Override
    public boolean isValid(PasswordRequest request, ConstraintValidatorContext constraintValidatorContext) {
        return request
                .getPassword()
                .equals(request.getMatchingPassword());
    }
}
