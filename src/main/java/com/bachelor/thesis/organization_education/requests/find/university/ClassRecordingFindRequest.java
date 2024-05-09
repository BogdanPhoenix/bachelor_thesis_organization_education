package com.bachelor.thesis.organization_education.requests.find.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import com.bachelor.thesis.organization_education.annotations.Trimmed;
import com.bachelor.thesis.organization_education.dto.GroupDiscipline;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ClassRecordingFindRequest implements FindRequest {
    @NotNull
    @NonNull
    private GroupDiscipline magazine;

    @NotNull
    @NonNull
    @Trimmed
    private String classTopic;
}
