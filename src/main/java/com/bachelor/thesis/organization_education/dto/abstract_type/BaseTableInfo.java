package com.bachelor.thesis.organization_education.dto.abstract_type;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseTableInfo {
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

    @Column(name = "enable")
    private boolean enabled;

    public abstract Response getResponse();
    public abstract void setId(UUID id);
    public abstract UUID getId();

    protected <T extends Response.ResponseBuilder<?, ?>> void initResponse(@NonNull T builder){
        builder.id(this.getId())
                .createDate(this.createDate)
                .updateDate(this.updateDate);
    }

    @PrePersist
    public void onPrePersist(){
        this.setEnabled(true);
        this.setCreateDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
    }
}
