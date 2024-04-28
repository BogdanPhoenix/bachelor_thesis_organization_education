package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.stereotype.Repository;
import com.bachelor.thesis.organization_education.dto.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bachelor.thesis.organization_education.dto.GroupDiscipline;
import com.bachelor.thesis.organization_education.dto.AcademicDiscipline;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface GroupDisciplineRepository extends JpaRepository<GroupDiscipline, UUID> {
    Optional<GroupDiscipline> findByGroupAndDiscipline(Group group, AcademicDiscipline discipline);
}
