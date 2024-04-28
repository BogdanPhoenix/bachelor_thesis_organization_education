package com.bachelor.thesis.organization_education.repositories.user;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bachelor.thesis.organization_education.dto.Lecturer;

import java.util.UUID;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, UUID> {

}
