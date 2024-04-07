package com.bachelor.thesis.organization_education.dto.abstract_type;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public abstract class NameEntity extends BaseTableInfo {
    @NonNull
    @NotBlank
    @Column(name = "en_name", nullable = false, unique = true)
    private String enName;

    @NonNull
    @NotBlank
    @Column(name = "ua_name", nullable = false, unique = true)
    private String uaName;
}
