package com.bachelor.thesis.organization_education.dto;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.bachelor.thesis.organization_education.dto.abstract_type.NameEntity;
import com.bachelor.thesis.organization_education.responces.university.AcademicDisciplineResponse;

import java.util.Set;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "academic_disciplines")
public class AcademicDiscipline extends NameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    protected UUID id;

    @Column(name = "amount_credits", nullable = false)
    private short amountCredits;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "disciplines", cascade = {PERSIST, MERGE, REFRESH})
    private List<Lecturer> lecturers;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "discipline", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<GroupDiscipline> groupsDisciplines;

    @Override
    public AcademicDisciplineResponse getResponse() {
        var builder = AcademicDisciplineResponse.builder();
        super.initResponse(builder);
        return builder
                .amountCredits(amountCredits)
                .build();
    }

    @PreRemove
    private void removeLecturerAssociations() {
        for(var lecturer : this.lecturers) {
            lecturer.getDisciplines().remove(this);
        }
    }
}
