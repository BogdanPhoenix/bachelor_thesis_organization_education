package com.bachelor.thesis.organization_education.requests.insert.abstracts;

import com.bachelor.thesis.organization_education.annotations.ValidNameEntity;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class NameEntityInsertRequest implements InsertRequest{
    @NotNull
    @ValidNameEntity
    @Size(max = 255)
    private String enName;
    @NotNull
    @ValidNameEntity
    @Size(max = 255)
    private String uaName;
}
