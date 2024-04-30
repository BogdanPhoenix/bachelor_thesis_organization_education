package com.bachelor.thesis.organization_education.repositories.user;

import org.springframework.stereotype.Repository;
import com.bachelor.thesis.organization_education.dto.Lecturer;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LecturerRepository extends BaseTableInfoRepository<Lecturer> {
    List<Lecturer> findAllById(UUID id);
}
