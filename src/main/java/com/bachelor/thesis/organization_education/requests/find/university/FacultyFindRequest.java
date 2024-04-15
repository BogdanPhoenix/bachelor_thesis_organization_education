package com.bachelor.thesis.organization_education.requests.find.university;

import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.requests.find.abstracts.NameEntityFindRequest;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FacultyFindRequest extends NameEntityFindRequest {
    @NonNull
    private University university;

    @Override
    public boolean skip() {
        return true;
    }
}
