package com.bachelor.thesis.organization_education.requests.find.university;

import com.bachelor.thesis.organization_education.requests.find.abstracts.NameEntityFindRequest;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UniversityFindRequest extends NameEntityFindRequest {
    private UUID adminId;
}
