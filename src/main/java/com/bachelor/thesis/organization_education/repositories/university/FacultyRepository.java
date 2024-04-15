package com.bachelor.thesis.organization_education.repositories.university;

import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.repositories.NameEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacultyRepository extends NameEntityRepository<Faculty> {
    @Query("SELECT f FROM Faculty f WHERE f.university = :university AND (f.enName LIKE :enName OR f.uaName LIKE :uaName)")
    Optional<Faculty> findFaculty(
            @Param("university") University university,
            @Param("enName") String enName,
            @Param("uaName") String uaName
    );
}
