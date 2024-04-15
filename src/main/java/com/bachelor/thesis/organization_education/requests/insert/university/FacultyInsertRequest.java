package com.bachelor.thesis.organization_education.requests.insert.university;

import com.bachelor.thesis.organization_education.requests.insert.abstracts.NameEntityInsertRequest;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FacultyInsertRequest extends NameEntityInsertRequest {

}
