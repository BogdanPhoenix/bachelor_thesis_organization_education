package com.bachelor.thesis.organization_education.responces.abstract_type;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;

/**
 * The abstract class NameEntityResponse is the base class for responses,
 * that contain entity names that can be represented in both Ukrainian and English.
 * This class extends the Response class and uses Lombok annotations to automatically generate getters, setters,
 * constructors, toString(), equals(), and hashCode() methods, which simplifies the work with objects.
 * The @MappedSuperclass annotation is used to mark the class as a base class for JPA entities.
 */
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class NameEntityResponse extends Response {
    private String enName;
    private String uaName;
}
