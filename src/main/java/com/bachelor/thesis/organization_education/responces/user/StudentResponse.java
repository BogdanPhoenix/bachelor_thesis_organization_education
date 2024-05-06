package com.bachelor.thesis.organization_education.responces.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.responces.university.GroupResponse;

import java.util.UUID;

@Setter
@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StudentResponse extends Response {
    @NonNull
    private UUID group;
}
