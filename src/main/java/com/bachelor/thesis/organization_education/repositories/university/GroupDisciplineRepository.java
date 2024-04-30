package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.stereotype.Repository;
import com.bachelor.thesis.organization_education.dto.Group;
import com.bachelor.thesis.organization_education.dto.GroupDiscipline;
import com.bachelor.thesis.organization_education.dto.AcademicDiscipline;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.List;

@Repository
public interface GroupDisciplineRepository extends BaseTableInfoRepository<GroupDiscipline> {
    List<GroupDiscipline> findAllByGroupAndDiscipline(Group group, AcademicDiscipline discipline);
}
