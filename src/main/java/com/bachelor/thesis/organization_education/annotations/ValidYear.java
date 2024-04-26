package com.bachelor.thesis.organization_education.annotations;

import java.lang.annotation.*;
import jakarta.validation.Payload;
import jakarta.validation.Constraint;
import com.bachelor.thesis.organization_education.validators.YearValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = YearValidator.class)
@Documented
public @interface ValidYear {
    String message() default "The year value cannot be less than 1900.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
