package com.bachelor.thesis.organization_education.annotations;

import jakarta.validation.Payload;
import jakarta.validation.Constraint;
import com.bachelor.thesis.organization_education.validators.NameValidator;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Користувацька анотація для перевірки імен користувачів.
 * Гарантує, що дані у вказаному запиті містять лише кириличні та латинські літери, дефіси та пробіли.
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NameValidator.class)
@Documented
public @interface ValidNameUser {
    String message() default "The data in the %s query should contain only Cyrillic and Latin letters, hyphens, and spaces.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
