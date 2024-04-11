package com.bachelor.thesis.organization_education.services.implementations;

import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.dto.abstract_type.NameEntity;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.repositories.NameEntityRepository;
import com.bachelor.thesis.organization_education.requests.abstract_type.NameEntityRequest;
import com.bachelor.thesis.organization_education.requests.abstract_type.Request;
import lombok.NonNull;

import java.util.Optional;

/**
 * An abstract class that contains an implementation for working with the name attributes of the entities that contain them.
 * @param <T> type of the entity class for which CRUD is implemented.
 * @param <J> the type of entity repository that manages access to and interconnection with the entity.
 */
public abstract class NameEntityServiceAbstract<T extends NameEntity, J extends NameEntityRepository<T>> extends CrudServiceAbstract<T, J> {
    protected NameEntityServiceAbstract(J repository, String tableName) {
        super(repository, tableName);
    }

    protected <B extends NameEntity.NameEntityBuilder<?, ?>> @NonNull B initEntity(B builder, Request request) {
        var nameEntityRequest = (NameEntityRequest) request;
        builder.uaName(nameEntityRequest.getUaName())
                .enName(nameEntityRequest.getEnName());
        return builder;
    }

    @Override
    protected T getEntity(@NonNull Request request) throws NotFindEntityInDataBaseException {
        return findEntity(request)
                .filter(BaseTableInfo::isEnabled)
                .orElseThrow(() -> new NotFindEntityInDataBaseException("The query failed to find an entity in the table: " + tableName));
    }

    @Override
    protected Optional<T> findEntity(@NonNull Request request) {
        var nameEntityRequest = (NameEntityRequest) request;

        return repository.findByEnNameOrUaName(
                nameEntityRequest.getEnName(),
                nameEntityRequest.getUaName()
        );
    }

    @Override
    protected void updateEntity(T entity, Request request) {
        var nameEntityRequest = (NameEntityRequest) request;

        if(!nameEntityRequest.getEnName().isBlank()) {
            entity.setEnName(nameEntityRequest.getEnName());
        }
        if(!nameEntityRequest.getUaName().isBlank()) {
            entity.setUaName(nameEntityRequest.getUaName());
        }
    }
}
