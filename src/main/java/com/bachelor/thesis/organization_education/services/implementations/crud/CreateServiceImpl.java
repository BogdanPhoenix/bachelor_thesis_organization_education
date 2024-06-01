package com.bachelor.thesis.organization_education.services.implementations.crud;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CreateService;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.List;
import java.util.Collection;
import java.util.function.Function;

/**
 * An abstract class that contains an implementation of create functionality common to all service classes.
 * @param <T> type of the entity class for which create operations are implemented.
 * @param <J> the type of entity repository that manages access to and interconnection with the entity.
 */
@Transactional
@RequiredArgsConstructor
public class CreateServiceImpl<T extends BaseTableInfo, J extends BaseTableInfoRepository<T>> implements CreateService {
    private final J repository;
    private final String tableName;
    private final Function<FindRequest, List<T>> findAll;
    private final Function<InsertRequest, T> createEntity;

    @Override
    public List<Response> addValue(@NonNull Collection<? extends InsertRequest> requests) throws DuplicateException, NullPointerException {
        return requests.stream()
                .map(this::addValue)
                .map(BaseTableInfo::getResponse)
                .toList();
    }

    @Override
    public T addValue(@NonNull InsertRequest request) throws DuplicateException, NullPointerException {
        if(isDuplicate(request.getFindRequest())){
            throw new DuplicateException(String.format("The \"%s\" entity is already present in the \"%s\" table.", request, tableName));
        }

        T newEntity = createEntity.apply(request);
        return repository.save(newEntity);
    }

    private boolean isDuplicate(FindRequest request){
        return !findAll.apply(request).isEmpty();
    }
}