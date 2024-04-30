package com.bachelor.thesis.organization_education.repositories.abstracts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;

import java.util.UUID;

@NoRepositoryBean
public interface BaseTableInfoRepository<T extends BaseTableInfo> extends JpaRepository<T, UUID> {
    @Query("SELECT b FROM #{#entityName} b WHERE b.enabled = true")
    Page<BaseTableInfo> findAllActiveEntities(Pageable pageable);
}
