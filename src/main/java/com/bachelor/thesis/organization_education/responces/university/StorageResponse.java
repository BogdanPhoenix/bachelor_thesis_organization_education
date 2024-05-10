package com.bachelor.thesis.organization_education.responces.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StorageResponse extends Response {
    @NonNull
    private UUID userId;

    @NonNull
    private UUID classRecording;

    @NonNull
    private String fileName;

    @NonNull
    private String fileType;
}
