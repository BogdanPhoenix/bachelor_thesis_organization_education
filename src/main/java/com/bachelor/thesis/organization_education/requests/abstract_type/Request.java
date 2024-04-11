package com.bachelor.thesis.organization_education.requests.abstract_type;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

/**
 * An abstract class representing the query structure that can be used to interact with the system.
 */
@ToString
@SuperBuilder
@MappedSuperclass
@EqualsAndHashCode
@NoArgsConstructor
@Validated
public abstract class Request {
    /**
     * Checks if the request is empty.
     *
     * @return true if the request does not contain at least one empty attribute, otherwise false.
     */
    public abstract boolean isEmpty();
}