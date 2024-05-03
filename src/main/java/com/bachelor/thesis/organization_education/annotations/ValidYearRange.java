package com.bachelor.thesis.organization_education.annotations;

import jakarta.validation.Payload;
import jakarta.validation.Constraint;
import com.bachelor.thesis.organization_education.validators.YearRangeValidator;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * Custom annotation for validating the range of years.
 * This annotation is applied to types and targets the runtime environment.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = YearRangeValidator.class)
public @interface ValidYearRange {
    String message() default "The value of the year in the startYear variable must be less than the value in the endYear variable.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
