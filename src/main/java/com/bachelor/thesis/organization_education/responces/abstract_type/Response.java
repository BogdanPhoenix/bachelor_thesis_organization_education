package com.bachelor.thesis.organization_education.responces.abstract_type;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * An abstract class that represents a response structure that can be used to interact with the system.
 */
@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public abstract class Response {
    public static final Long EMPTY_PARENT_ENTITY = 0L;

    private Long id;

    @EqualsAndHashCode.Exclude
    private LocalDateTime createDate;

    @EqualsAndHashCode.Exclude
    private LocalDateTime updateDate;

    /**
     * Checks if the entity is empty.
     *
     * @return true if the entity does not contain at least one empty attribute, otherwise false.
     */
    public abstract boolean isEmpty();

    protected static <T extends ResponseBuilder<?, ?>> @NonNull T initEmpty(@NonNull T builder) {
        builder.createDate(LocalDateTime.MIN)
                .updateDate(LocalDateTime.MIN);
        return builder;
    }
}
