package com.bachelor.thesis.organization_education.requests.general.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.enums.AcademicTitle;
import com.bachelor.thesis.organization_education.enums.AcademicDegree;
import com.bachelor.thesis.organization_education.requests.general.abstracts.Request;
import com.bachelor.thesis.organization_education.requests.find.user.LecturerFindRequest;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LecturerRequest implements Request {
    @NonNull
    private AcademicTitle title;

    @NonNull
    private AcademicDegree degree;

    @NonNull
    private Faculty faculty;

    @NonNull
    private UUID userId;

    @Override
    public LecturerFindRequest getFindRequest() {
        return LecturerFindRequest.builder()
                .userId(userId)
                .build();
    }
}
