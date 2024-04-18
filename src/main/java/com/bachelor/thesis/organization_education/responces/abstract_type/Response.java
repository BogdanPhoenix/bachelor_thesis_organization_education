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
    private Long id;

    @EqualsAndHashCode.Exclude
    private LocalDateTime createDate;

    @EqualsAndHashCode.Exclude
    private LocalDateTime updateDate;

    protected static <T extends ResponseBuilder<?, ?>> @NonNull T initEmpty(@NonNull T builder) {
        builder.createDate(LocalDateTime.MIN)
                .updateDate(LocalDateTime.MIN);
        return builder;
    }
}
