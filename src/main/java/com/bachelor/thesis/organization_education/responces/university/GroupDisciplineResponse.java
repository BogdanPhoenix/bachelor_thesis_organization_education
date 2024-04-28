package com.bachelor.thesis.organization_education.responces.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GroupDisciplineResponse extends Response {
    @NonNull
    private GroupResponse group;
    @NonNull
    private AcademicDisciplineResponse discipline;
    private short amountPractical;
    private short amountLecture;
}
