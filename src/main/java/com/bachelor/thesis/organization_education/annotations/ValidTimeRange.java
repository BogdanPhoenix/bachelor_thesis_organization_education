package com.bachelor.thesis.organization_education.annotations;

import jakarta.validation.Payload;
import jakarta.validation.Constraint;
import com.bachelor.thesis.organization_education.validators.TimeRangeValidator;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * An annotation used to validate the time range between two fields.
 * Checks if the value in the startTime field precedes the value in the endTime field.
 * If the check fails, an error message is generated.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeRangeValidator.class)
public @interface ValidTimeRange {
    String message() default "The value in the startTime variable does not precede the value in the endTime variable.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
