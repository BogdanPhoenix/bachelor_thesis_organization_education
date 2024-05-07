package com.bachelor.thesis.organization_education.responces.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class EvaluationsForClassesResponse {
    @NonNull
    private ClassRecordingResponse classRecording;

    @NonNull
    private Set<StudentEvaluationResponse> studentEvaluations;
}
