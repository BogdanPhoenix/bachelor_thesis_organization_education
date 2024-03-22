package com.bachelor.thesis.organization_education.validators;

import com.bachelor.thesis.organization_education.annotations.ValidPassword;
import com.bachelor.thesis.organization_education.enums.PatternTemplate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return validatePassword(password);
    }

    private boolean validatePassword(String password) {
        var pattern = Pattern.compile(PatternTemplate.PASSWORD.getValue());
        var matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
