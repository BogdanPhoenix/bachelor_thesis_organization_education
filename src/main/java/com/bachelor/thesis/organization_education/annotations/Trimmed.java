package com.bachelor.thesis.organization_education.annotations;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * An annotation marker to indicate fields where you want to remove spaces at the beginning and end.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Trimmed {
}
