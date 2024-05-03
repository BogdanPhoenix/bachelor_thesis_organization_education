package com.bachelor.thesis.organization_education.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.bachelor.thesis.organization_education.annotations.ValidTimeRange;
import com.bachelor.thesis.organization_education.requests.general.abstracts.TimeRange;

public class TimeRangeValidator implements ConstraintValidator<ValidTimeRange, TimeRange> {
    @Override
    public boolean isValid(TimeRange value, ConstraintValidatorContext context) {
        var startTime = value.getStartTime();
        var endTime = value.getEndTime();

        return startTime != null && endTime != null && startTime.before(endTime);
    }
}
