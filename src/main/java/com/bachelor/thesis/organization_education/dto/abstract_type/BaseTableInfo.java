package com.bachelor.thesis.organization_education.dto.abstract_type;

import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

    @Column(name = "enable")
    private boolean enabled;

    public abstract Response getResponse();

    protected <T extends Response.ResponseBuilder<?, ?>> void initResponse(@NonNull T builder){
        builder.id(this.id)
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
