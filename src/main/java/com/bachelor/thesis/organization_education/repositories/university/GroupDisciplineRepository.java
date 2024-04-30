package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.stereotype.Repository;
import com.bachelor.thesis.organization_education.dto.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bachelor.thesis.organization_education.dto.GroupDiscipline;
import com.bachelor.thesis.organization_education.dto.AcademicDiscipline;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupDisciplineRepository extends JpaRepository<GroupDiscipline, UUID> {
    List<GroupDiscipline> findAllByGroupAndDiscipline(Group group, AcademicDiscipline discipline);
}
