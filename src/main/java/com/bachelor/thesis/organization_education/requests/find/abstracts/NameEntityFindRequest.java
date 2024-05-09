package com.bachelor.thesis.organization_education.requests.find.abstracts;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Size;
import jakarta.persistence.MappedSuperclass;
import com.bachelor.thesis.organization_education.annotations.Trimmed;
import com.bachelor.thesis.organization_education.annotations.ValidNameEntity;

/**
 * The abstract class NameEntityFindRequest is the base class for search request objects,
 * that refer to entities with names that can be represented in both Ukrainian and English.
 * This class uses Lombok annotations to automatically generate getters, setters, constructors,
 * toString(), equals(), and hashCode() methods, which simplifies the work with objects.
 * Also, the @MappedSuperclass annotation is used to mark the class as a base class for JPA entities.
 */
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class NameEntityFindRequest implements FindRequest {
    @ValidNameEntity
    @Size(max = 255)
    @Trimmed
    private String enName;

    @ValidNameEntity
    @Size(max = 255)
    @Trimmed
    private String uaName;
}
