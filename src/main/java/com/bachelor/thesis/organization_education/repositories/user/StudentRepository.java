package com.bachelor.thesis.organization_education.repositories.user;

import org.springframework.stereotype.Repository;
import com.bachelor.thesis.organization_education.dto.Student;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends BaseTableInfoRepository<Student> {
    List<Student> findAllById(UUID id);
}
