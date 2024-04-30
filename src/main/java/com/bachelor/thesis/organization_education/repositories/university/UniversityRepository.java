package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.stereotype.Repository;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.repositories.NameEntityRepository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface UniversityRepository extends NameEntityRepository<University> {
    Optional<University> findByAdminId(UUID adminId);
    List<University> findAllByAdminIdOrEnNameOrUaName(UUID adminId, String enName, String uaName);
}
