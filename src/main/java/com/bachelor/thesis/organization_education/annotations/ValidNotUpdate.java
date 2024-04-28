package com.bachelor.thesis.organization_education.annotations;

import jakarta.validation.Payload;
import jakarta.validation.Constraint;
import com.bachelor.thesis.organization_education.validators.ValidatorUpdate;

import java.lang.annotation.Target;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidatorUpdate.class)
@Documented
public @interface ValidNotUpdate {
    String message() default "Changing the values of a variable is prohibited. You can pass data to a variable only when creating an entity.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
