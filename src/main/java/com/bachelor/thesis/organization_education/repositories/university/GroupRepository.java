package com.bachelor.thesis.organization_education.repositories.university;

import org.springframework.stereotype.Repository;
import com.bachelor.thesis.organization_education.dto.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bachelor.thesis.organization_education.dto.Specialty;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    Optional<Group> findBySpecialtyAndYearStartAndReducedForm(Specialty specialty, short yearStart, boolean reducedForm);
}
