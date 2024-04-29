package com.bachelor.thesis.organization_education.annotations;

import jakarta.validation.Payload;
import jakarta.validation.Constraint;
import com.bachelor.thesis.organization_education.validators.ValidatorAllowedChars;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom validation annotation to enforce that the data in the annotated variable
 * contains only Cyrillic and Latin letters, numbers, hyphens, spaces, and the number symbol (№).
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidatorAllowedChars.class)
@Documented
public @interface ValidAllowedChars {
    String message() default "The data in the variable must contain only Cyrillic and Latin letters, numbers, hyphens, spaces, and the number symbol (№).";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
