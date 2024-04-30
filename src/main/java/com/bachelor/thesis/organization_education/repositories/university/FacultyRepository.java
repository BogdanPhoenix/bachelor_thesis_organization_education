package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.repositories.NameEntityRepository;

import java.util.List;

@Repository
public interface FacultyRepository extends NameEntityRepository<Faculty> {
    @Query("SELECT f FROM Faculty f WHERE f.university = :university AND (f.enName LIKE :enName OR f.uaName LIKE :uaName)")
    List<Faculty> findAllFaculty(
            @Param("university") University university,
            @Param("enName") String enName,
            @Param("uaName") String uaName
    );
}
