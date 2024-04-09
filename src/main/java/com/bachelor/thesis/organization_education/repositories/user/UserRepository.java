package com.bachelor.thesis.organization_education.repositories.user;

import com.bachelor.thesis.organization_education.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByUserId(UUID userId);
}
