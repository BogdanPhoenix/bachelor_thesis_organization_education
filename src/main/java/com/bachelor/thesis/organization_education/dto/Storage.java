package com.bachelor.thesis.organization_education.dto;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.university.StorageResponse;

import java.util.UUID;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Table(name = "files",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "record_id", "file_name"})
)
public class Storage extends BaseTableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    protected UUID id;

    @NonNull
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private ClassRecording classRecording;

    @NonNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @NonNull
    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Lob
    @Column(name = "file_data", nullable = false)
    private byte[] fileData;

    @Override
    public StorageResponse getResponse() {
        var builder = StorageResponse.builder();
        super.initResponse(builder);
        return builder
                .userId(userId)
                .classRecording(classRecording.getId())
                .fileName(fileName)
                .fileType(fileType)
                .build();
    }
}
