package com.bachelor.thesis.organization_education.dto;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;

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
@Table(name = "files")
public class File extends BaseTableInfo {
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
    @Column(name = "path_to_file", nullable = false, unique = true)
    private String pathToFile;

    @Override
    public Response getResponse() {
        return null;
    }
}
