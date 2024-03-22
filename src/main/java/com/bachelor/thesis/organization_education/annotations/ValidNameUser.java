package com.bachelor.thesis.organization_education.annotations;

import com.bachelor.thesis.organization_education.validators.NameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NameValidator.class)
@Documented
public @interface ValidNameUser {
    String message() default "The data in the %s query should contain only Cyrillic and Latin letters, hyphens, and spaces.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
