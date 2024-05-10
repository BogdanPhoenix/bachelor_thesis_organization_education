package com.bachelor.thesis.organization_education.requests.find.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import com.bachelor.thesis.organization_education.dto.ClassRecording;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class StorageFindRequest implements FindRequest {
    @NotNull
    @NonNull
    private UUID userId;

    @NotNull
    @NonNull
    private ClassRecording classRecording;

    @NotNull
    @NonNull
    private String fileName;
}
