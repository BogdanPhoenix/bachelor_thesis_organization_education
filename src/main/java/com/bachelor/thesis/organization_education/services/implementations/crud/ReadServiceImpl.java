package com.bachelor.thesis.organization_education.services.implementations.crud;

import com.bachelor.thesis.organization_education.repositories.university.AudienceRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.crud.ReadService;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Transactional
@RequiredArgsConstructor
public class ReadServiceImpl<T extends BaseTableInfo, J extends BaseTableInfoRepository<T>> implements ReadService {
    private final J repository;
    private final String tableName;
    private final Function<UUID, Optional<T>> findValueById;
    private final Function<FindRequest, List<T>> findAll;

    @Override
    public T getValue(@NonNull FindRequest request) throws NotFindEntityInDataBaseException {
        return findAll.apply(request)
                .stream()
                .filter(BaseTableInfo::isEnabled)
                .findFirst()
                .orElseThrow(() -> new NotFindEntityInDataBaseException(String.format("No entities were found for the query: %s could not find any entities in the table: %s.", request, tableName)));
    }

    @Override
    public T getValue(@NonNull UUID id) throws NotFindEntityInDataBaseException {
        return findValueById.apply(id)
                .orElseThrow(() -> new NotFindEntityInDataBaseException(String.format("Unable to find an entity in the \"%s\" table using the specified identifier: %s.", tableName, id)));
    }

    @Override
    public Page<Response> getAll(Pageable pageable) {
        return repository.findAllActiveEntities(pageable)
                .map(BaseTableInfo::getResponse);
    }
}
