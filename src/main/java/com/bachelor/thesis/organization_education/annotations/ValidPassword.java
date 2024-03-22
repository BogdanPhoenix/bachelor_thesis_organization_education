package com.bachelor.thesis.organization_education.annotations;

import com.bachelor.thesis.organization_education.validators.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface ValidPassword {
    String message() default """
            The password you entered: "%s" has not been validated. It must meet the following rules:
                - Minimum 8 characters
                - At least one capital letter
                - At least one lowercase letter
                - At least one number
                - Allowed special characters: !@#$&*_
            """;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
