package com.bachelor.thesis.organization_education.requests.general.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import com.bachelor.thesis.organization_education.dto.Group;
import com.bachelor.thesis.organization_education.dto.AcademicDiscipline;
import com.bachelor.thesis.organization_education.annotations.ValidNotUpdate;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.find.university.GroupDisciplineFindRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@GroupSequence({GroupDisciplineRequest.class, InsertRequest.class, UpdateRequest.class})
public class GroupDisciplineRequest implements InsertRequest, UpdateRequest {
    @NotNull(groups = InsertRequest.class)
    @ValidNotUpdate(groups = UpdateRequest.class)
    private Group group;

    @NotNull(groups = InsertRequest.class)
    @ValidNotUpdate(groups = UpdateRequest.class)
    private AcademicDiscipline discipline;

    @Min(value = 0, groups = {InsertRequest.class, UpdateRequest.class})
    @Max(value = 100, groups = {InsertRequest.class, UpdateRequest.class})
    private short amountPractical;

    @Min(value = 0, groups = {InsertRequest.class, UpdateRequest.class})
    @Max(value = 100, groups = {InsertRequest.class, UpdateRequest.class})
    private short amountLecture;

    @Override
    public GroupDisciplineFindRequest getFindRequest() {
        return GroupDisciplineFindRequest.builder()
                .group(group)
                .discipline(discipline)
                .build();
    }

    public boolean amountPracticalIsEmpty() {
        return amountPractical <= 0;
    }

    public boolean amountLectureIsEmpty() {
        return amountLecture <= 0;
    }
}
