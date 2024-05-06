package com.bachelor.thesis.organization_education.responces.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class MagazineResponse {
    @NonNull
    private GroupDisciplineResponse groupDiscipline;

    @NonNull
    private Map<ClassRecordingResponse, Set<StudentEvaluationResponse>> evaluationMap;
}
