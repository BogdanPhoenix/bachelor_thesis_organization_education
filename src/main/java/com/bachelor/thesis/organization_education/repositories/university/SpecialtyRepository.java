package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.stereotype.Repository;
import com.bachelor.thesis.organization_education.dto.Specialty;
import com.bachelor.thesis.organization_education.repositories.NameEntityRepository;

import java.util.List;

@Repository
public interface SpecialtyRepository extends NameEntityRepository<Specialty> {
    List<Specialty> findAllByEnNameOrUaNameOrNumber(String enName, String uaName, short number);
}
