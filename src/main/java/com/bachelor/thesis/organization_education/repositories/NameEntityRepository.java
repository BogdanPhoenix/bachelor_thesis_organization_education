package com.bachelor.thesis.organization_education.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bachelor.thesis.organization_education.dto.abstract_type.NameEntity;

import java.util.List;
import java.util.UUID;

@NoRepositoryBean
public interface NameEntityRepository<T extends NameEntity> extends JpaRepository<T, UUID> {
    List<T> findAllByEnNameOrUaName(String enName, String uaName);
}
