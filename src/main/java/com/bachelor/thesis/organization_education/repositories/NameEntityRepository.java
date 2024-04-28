package com.bachelor.thesis.organization_education.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bachelor.thesis.organization_education.dto.abstract_type.NameEntity;

import java.util.UUID;
import java.util.Optional;

@NoRepositoryBean
public interface NameEntityRepository<T extends NameEntity> extends JpaRepository<T, UUID> {
    Optional<T> findByEnNameOrUaName(String enName, String uaName);
}
