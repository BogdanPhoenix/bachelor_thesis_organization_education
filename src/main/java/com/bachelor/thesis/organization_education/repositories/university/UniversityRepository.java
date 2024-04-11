package com.bachelor.thesis.organization_education.repositories.university;

import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.repositories.NameEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends NameEntityRepository<University> {
}
