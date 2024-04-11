package com.bachelor.thesis.organization_education.requests.university;

import com.bachelor.thesis.organization_education.requests.abstract_type.NameEntityRequest;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UniversityFindRequest extends NameEntityRequest {
}
