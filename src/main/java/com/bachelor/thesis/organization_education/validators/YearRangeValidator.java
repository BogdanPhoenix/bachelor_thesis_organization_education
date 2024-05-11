package com.bachelor.thesis.organization_education.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.bachelor.thesis.organization_education.annotations.ValidYearRange;
import com.bachelor.thesis.organization_education.requests.general.abstracts.YearRange;

public class YearRangeValidator implements ConstraintValidator<ValidYearRange, YearRange> {
    @Override
    public boolean isValid(YearRange value, ConstraintValidatorContext context) {
        return value.getYearStart() == null ||
                value.getYearEnd() == null ||
                value.getYearEnd() >= value.getYearStart();
    }
}
