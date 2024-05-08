package com.bachelor.thesis.organization_education.requests.find.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import com.bachelor.thesis.organization_education.dto.UniversityGroup;
import com.bachelor.thesis.organization_education.dto.AcademicDiscipline;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GroupDisciplineFindRequest implements FindRequest {
    @NotNull
    @NonNull
    private UniversityGroup group;

    @NotNull
    @NonNull
    private AcademicDiscipline discipline;
}
