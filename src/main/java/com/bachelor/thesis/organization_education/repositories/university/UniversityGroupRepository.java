package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import com.bachelor.thesis.organization_education.dto.Specialty;
import com.bachelor.thesis.organization_education.dto.UniversityGroup;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UniversityGroupRepository extends BaseTableInfoRepository<UniversityGroup> {
    List<UniversityGroup> findAllBySpecialtyAndYearStartAndReducedForm(Specialty specialty, short yearStart, boolean reducedForm);

    @Query("""
        SELECT ug FROM UniversityGroup ug
        JOIN Faculty f ON ug.faculty = f
        JOIN University u ON f.university = u
        WHERE u.adminId = :adminId AND ug.enabled = TRUE
    """)
    Page<UniversityGroup> findAll(UUID adminId, Pageable pageable);
}
