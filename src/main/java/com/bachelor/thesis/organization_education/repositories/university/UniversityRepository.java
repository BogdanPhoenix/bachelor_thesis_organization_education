package com.bachelor.thesis.organization_education.repositories.university;

import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.repositories.NameEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UniversityRepository extends NameEntityRepository<University> {
    Optional<University> findByAdminId(UUID adminId);
}
