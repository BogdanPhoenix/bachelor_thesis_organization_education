package com.bachelor.thesis.organization_education.dto;

import com.bachelor.thesis.organization_education.dto.abstract_type.NameEntity;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.DETACH;

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
@Table(name = "universities")
public class University extends NameEntity {
    @NonNull
    @ManyToOne
    @JoinColumn(name = "accreditation_id", nullable = false)
    private AccreditationLevel accreditationLevel;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "university", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Audience> audiences;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "university", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Faculty> faculties;

    @Override
    public Response getResponse() {
        return null;
    }
}
