package com.bachelor.thesis.organization_education.annotations;

import jakarta.validation.Payload;
import jakarta.validation.Constraint;
import com.bachelor.thesis.organization_education.validators.EmailValidator;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to validate email addresses.
 * Validates that the email address provided adheres to standard formatting rules.
 * Example: "username@domain.domain_zone"
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
    String message() default """
            The email address you provided has not been validated. It must comply with the following rules:
                - The main body must contain only letters (uppercase or lowercase) of the Latin alphabet, numbers, or the symbols '_', '.', '+', or '-'.
                - The '@' symbol must be followed by at least one character, which can be a letter (uppercase or lowercase) of the Latin alphabet, a number, or a dash.
                - The dot (.) in the domain must be followed by at least one character, which can be a letter (uppercase or lowercase) of the Latin alphabet, a number, a dash or a period.
            Your email address should look like: "username@domain.domain_zone"
            """;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
