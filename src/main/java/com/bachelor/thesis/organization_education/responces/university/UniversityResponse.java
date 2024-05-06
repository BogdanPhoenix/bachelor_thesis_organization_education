package com.bachelor.thesis.organization_education.responces.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;
import com.bachelor.thesis.organization_education.enums.AccreditationLevel;
import com.bachelor.thesis.organization_education.responces.abstract_type.NameEntityResponse;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UniversityResponse extends NameEntityResponse {
    @NonNull
    private AccreditationLevel accreditationLevel;

    @NonNull
    private UUID adminId;
}
