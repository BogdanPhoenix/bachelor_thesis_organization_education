package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FacultyRepository extends BaseTableInfoRepository<Faculty> {
    @Query("SELECT f FROM Faculty f WHERE f.university = :university AND (f.enName LIKE :enName OR f.uaName LIKE :uaName)")
    List<Faculty> findAllFaculty(
            @Param("university") University university,
            @Param("enName") String enName,
            @Param("uaName") String uaName
    );

    @Query("""
        SELECT f FROM Faculty f
        JOIN University u ON f.university = u
        WHERE u.adminId = :adminId AND f.enabled = TRUE
    """)
    Page<Faculty> findAll(UUID adminId, Pageable pageable);
}
