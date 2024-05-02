package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.stereotype.Repository;
import com.bachelor.thesis.organization_education.dto.Audience;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.List;

@Repository
public interface AudienceRepository extends BaseTableInfoRepository<Audience> {
    List<Audience> findAllByUniversityAndNumFloorAndNumAudience(University university, short numFloor, short numAudience);
}
