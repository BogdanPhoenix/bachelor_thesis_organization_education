package com.bachelor.thesis.organization_education.repositories.user;

import com.bachelor.thesis.organization_education.dto.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    Optional<Lecturer> findByUser(UUID userId);
}
