package com.bachelor.thesis.organization_education.services.implementations.crud;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.services.interfaces.crud.DeleteService;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.UUID;

@Transactional
@RequiredArgsConstructor
public class DeleteServiceImpl<T extends BaseTableInfo, J extends BaseTableInfoRepository<T>> implements DeleteService {
    private final J repository;

    @Override
    public void deleteValue(@NonNull UUID id) {
        repository.findById(id)
                .ifPresent(repository::delete);
    }
}
