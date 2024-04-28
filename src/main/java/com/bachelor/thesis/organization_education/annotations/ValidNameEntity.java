package com.bachelor.thesis.organization_education.annotations;

import jakarta.validation.Payload;
import jakarta.validation.Constraint;
import com.bachelor.thesis.organization_education.validators.NameEntityValidator;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation for validating name entities.
 * Ensures that the data in the annotated variable contains only Cyrillic and Latin letters, hyphens, and spaces.
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NameEntityValidator.class)
@Documented
public @interface ValidNameEntity {
    String message() default "The data in the variable should contain only Cyrillic and Latin letters, hyphens, and spaces.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
