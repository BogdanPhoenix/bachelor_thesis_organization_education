package com.bachelor.thesis.organization_education.requests.general.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.annotations.ProhibitValueAssignment;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.find.university.AudienceFindRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@GroupSequence({AudienceRequest.class, InsertRequest.class, UpdateRequest.class})
public class AudienceRequest implements InsertRequest, UpdateRequest {
    @ProhibitValueAssignment(groups = {InsertRequest.class, UpdateRequest.class})
    private University university;

    @Min(value = 0, groups = InsertRequest.class)
    @Max(value = 30, groups = InsertRequest.class)
    @NotNull(groups = InsertRequest.class)
    @ProhibitValueAssignment(groups = UpdateRequest.class)
    private Short numFloor;

    @Min(value = 0, groups = InsertRequest.class)
    @Max(value = 1000, groups = InsertRequest.class)
    @NotNull(groups = InsertRequest.class)
    @ProhibitValueAssignment(groups = UpdateRequest.class)
    private Short numAudience;

    @Min(value = 0, groups = {InsertRequest.class, UpdateRequest.class})
    @Max(value = 100, groups = {InsertRequest.class, UpdateRequest.class})
    @NotNull(groups = InsertRequest.class)
    private Short numSeats;

    @Override
    public AudienceFindRequest getFindRequest() {
        return AudienceFindRequest.builder()
                .university(university)
                .numFloor(numFloor)
                .numAudience(numAudience)
                .build();
    }

    public boolean numSeatsIsEmpty() {
        return numSeats == 0;
    }
}
