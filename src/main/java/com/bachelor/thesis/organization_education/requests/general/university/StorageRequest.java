package com.bachelor.thesis.organization_education.requests.general.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import com.bachelor.thesis.organization_education.dto.ClassRecording;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.find.university.StorageFindRequest;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class StorageRequest implements InsertRequest, UpdateRequest {
    private UUID userId;
    private ClassRecording classRecording;
    private String fileName;
    private String fileType;
    private byte[] fileData;

    @Override
    public StorageFindRequest getFindRequest() {
        return StorageFindRequest.builder()
                .userId(userId)
                .classRecording(classRecording)
                .fileName(fileName)
                .build();
    }
}
