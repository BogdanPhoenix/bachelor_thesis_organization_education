package com.bachelor.thesis.organization_education.responces.abstract_type;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

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
    private UUID id;

    @EqualsAndHashCode.Exclude
    private LocalDateTime createDate;

    @EqualsAndHashCode.Exclude
    private LocalDateTime updateDate;
}
