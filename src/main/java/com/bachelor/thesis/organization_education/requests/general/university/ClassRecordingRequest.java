package com.bachelor.thesis.organization_education.requests.general.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import com.bachelor.thesis.organization_education.dto.GroupDiscipline;
import com.bachelor.thesis.organization_education.annotations.ValidAllowedChars;
import com.bachelor.thesis.organization_education.annotations.ProhibitValueAssignment;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.find.university.ClassRecordingFindRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@GroupSequence({ClassRecordingRequest.class, InsertRequest.class, UpdateRequest.class})
public class ClassRecordingRequest implements InsertRequest, UpdateRequest {
    @NotNull(groups = InsertRequest.class)
    @ProhibitValueAssignment(groups = UpdateRequest.class)
    private GroupDiscipline magazine;

    @NotNull(groups = InsertRequest.class)
    @NotBlank(groups = InsertRequest.class)
    @Size(max = 255, groups = {InsertRequest.class, UpdateRequest.class})
    @ValidAllowedChars(groups = {InsertRequest.class, UpdateRequest.class})
    private String classTopic;

    @NotNull(groups = InsertRequest.class)
    @NotBlank(groups = InsertRequest.class)
    @Size(max = 1000, groups = {InsertRequest.class, UpdateRequest.class})
    @ValidAllowedChars(groups = {InsertRequest.class, UpdateRequest.class})
    private String description;

    @Override
    public ClassRecordingFindRequest getFindRequest() {
        return ClassRecordingFindRequest.builder()
                .magazine(magazine)
                .classTopic(classTopic)
                .build();
    }

    public boolean classTopicIsEmpty() {
        return classTopic == null || classTopic.isBlank();
    }

    public boolean descriptionIsEmpty() {
        return description == null || description.isBlank();
    }
}
