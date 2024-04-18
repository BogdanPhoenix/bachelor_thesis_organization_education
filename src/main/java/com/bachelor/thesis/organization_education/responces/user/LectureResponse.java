package com.bachelor.thesis.organization_education.responces.user;

import com.bachelor.thesis.organization_education.enums.AcademicDegree;
import com.bachelor.thesis.organization_education.enums.AcademicTitle;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.responces.university.FacultyResponse;
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
public class LectureResponse extends Response {
    @NonNull
    private UUID userId;

    @NonNull
    private AcademicTitle title;

    @NonNull
    private AcademicDegree degree;

    @NonNull
    private FacultyResponse faculty;
}
