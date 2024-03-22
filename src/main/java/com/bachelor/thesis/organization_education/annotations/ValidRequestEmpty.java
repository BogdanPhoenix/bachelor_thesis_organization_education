package com.bachelor.thesis.organization_education.annotations;

import com.bachelor.thesis.organization_education.validators.RequestEmptyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = RequestEmptyValidator.class)
@Documented
public @interface ValidRequestEmpty {
    String message() default "You sent an empty request.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
