package com.bachelor.thesis.organization_education.repositories.abstracts;

import org.springframework.data.repository.NoRepositoryBean;
import com.bachelor.thesis.organization_education.dto.abstract_type.NameEntity;

import java.util.List;

@NoRepositoryBean
public interface NameEntityRepository<T extends NameEntity> extends BaseTableInfoRepository<T> {
    List<T> findAllByEnNameOrUaName(String enName, String uaName);
}
