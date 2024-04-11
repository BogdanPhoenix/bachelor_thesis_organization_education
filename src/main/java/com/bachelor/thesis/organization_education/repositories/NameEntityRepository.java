package com.bachelor.thesis.organization_education.repositories;

import com.bachelor.thesis.organization_education.dto.abstract_type.NameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface NameEntityRepository<T extends NameEntity> extends JpaRepository<T, Long> {
    Optional<T> findByEnNameOrUaName(String enName, String uaName);
}
