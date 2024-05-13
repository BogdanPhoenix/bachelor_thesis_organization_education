package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import com.bachelor.thesis.organization_education.dto.Audience;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AudienceRepository extends BaseTableInfoRepository<Audience> {
    List<Audience> findAllByUniversityAndNumFloorAndNumAudience(University university, short numFloor, short numAudience);

    @Query("""
        SELECT a FROM Audience a
        JOIN University u ON a.university = u
        WHERE u.adminId = :adminId AND a.enabled = TRUE
    """)
    Page<Audience> findAll(UUID adminId, Pageable pageable);
}
