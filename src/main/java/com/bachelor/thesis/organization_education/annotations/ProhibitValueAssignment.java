package com.bachelor.thesis.organization_education.annotations;

import jakarta.validation.Payload;
import jakarta.validation.Constraint;
import com.bachelor.thesis.organization_education.validators.ValidatorProhibitValueAssignment;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation for marking fields that should not be updated.
 * This annotation can be applied to classes, fields, or other annotations.
 * When applied to a field, it indicates that the field should not be modified after creation.
 * Attempting to update such fields will result in a validation error.
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidatorProhibitValueAssignment.class)
@Documented
public @interface ProhibitValueAssignment {
    String message() default "Passing data to a variable from the outside is prohibited.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
