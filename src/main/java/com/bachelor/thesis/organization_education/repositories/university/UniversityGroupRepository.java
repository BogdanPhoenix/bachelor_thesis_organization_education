package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.stereotype.Repository;
import com.bachelor.thesis.organization_education.dto.Specialty;
import com.bachelor.thesis.organization_education.dto.UniversityGroup;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.List;

@Repository
public interface UniversityGroupRepository extends BaseTableInfoRepository<UniversityGroup> {
    List<UniversityGroup> findAllBySpecialtyAndYearStartAndReducedForm(Specialty specialty, short yearStart, boolean reducedForm);
}
