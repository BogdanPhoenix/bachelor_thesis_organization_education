package com.bachelor.thesis.organization_education.responces.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;
import com.bachelor.thesis.organization_education.responces.user.LecturerResponse;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GroupResponse extends Response {
    private LecturerResponse curator;
    private SpecialtyResponse specialty;
    private FacultyResponse faculty;
    private short yearStart;
    private short yearEnd;
    private boolean reducedForm;
}
