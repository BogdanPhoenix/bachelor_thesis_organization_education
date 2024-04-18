package com.bachelor.thesis.organization_education.repositories.user;

import com.bachelor.thesis.organization_education.dto.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Optional<Lecture> findByUser(UUID userId);
}
