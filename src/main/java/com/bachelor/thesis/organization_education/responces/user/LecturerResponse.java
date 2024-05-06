package com.bachelor.thesis.organization_education.responces.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;
import com.bachelor.thesis.organization_education.enums.AcademicTitle;
import com.bachelor.thesis.organization_education.enums.AcademicDegree;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.responces.university.FacultyResponse;
import com.bachelor.thesis.organization_education.responces.university.AcademicDisciplineResponse;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LecturerResponse extends Response {
    @NonNull
    private AcademicTitle title;

    @NonNull
    private AcademicDegree degree;

    @NonNull
    private UUID faculty;

    @NonNull
    private List<AcademicDisciplineResponse> disciplines;
}
