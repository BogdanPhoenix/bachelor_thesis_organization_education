package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.stereotype.Repository;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.dto.AcademicYear;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.List;

@Repository
public interface AcademicYearRepository extends BaseTableInfoRepository<AcademicYear> {
    List<AcademicYear> findAllByUniversityAndStartYearAndEndYear(University university, short startYear, short endYear);
}
