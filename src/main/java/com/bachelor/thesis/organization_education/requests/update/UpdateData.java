package com.bachelor.thesis.organization_education.requests.update;

import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * A class that represents data to be updated with a mandatory identifier and a new value.
 * Uses Lombok annotations to automatically create getters, setters, constructors, toString, and equals/hashCode methods.
 * Used as a base class for update models with the specified query type.
 *
 * @param <R> type of update request object
 */
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UpdateData<R extends UpdateRequest> {
    @NotNull
    private Long id;
    @NotNull
    private R newValue;
}
