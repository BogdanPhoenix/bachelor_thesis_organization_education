package com.bachelor.thesis.organization_education.services.implementations.crud;

import com.bachelor.thesis.organization_education.dto.abstract_type.NameEntity;
import com.bachelor.thesis.organization_education.repositories.NameEntityRepository;
import com.bachelor.thesis.organization_education.requests.find.university.UniversityFindRequest;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.NameEntityRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.Request;
import com.bachelor.thesis.organization_education.requests.update.abstracts.NameEntityUpdateRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import lombok.NonNull;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

/**
 * An abstract class that contains an implementation for working with the name attributes of the entities that contain them.
 * @param <T> type of the entity class for which CRUD is implemented.
 * @param <J> the type of entity repository that manages access to and interconnection with the entity.
 */
public abstract class NameEntityServiceAbstract<T extends NameEntity, J extends NameEntityRepository<T>> extends CrudServiceAbstract<T, J> {
    protected NameEntityServiceAbstract(J repository, String tableName) {
        this(repository, tableName, null);
    }

    protected NameEntityServiceAbstract(J repository, String tableName, ApplicationContext context) {
        super(repository, tableName, context);
    }

    protected <B extends NameEntity.NameEntityBuilder<?, ?>> @NonNull B initEntity(B builder, Request request) {
        var nameEntityRequest = (NameEntityRequest) request;
        builder.uaName(nameEntityRequest.getUaName())
                .enName(nameEntityRequest.getEnName());
        return builder;
    }

    @Override
    protected Optional<T> findEntity(@NonNull FindRequest request) {
        var nameEntityRequest = (UniversityFindRequest) request;

        return repository.findByEnNameOrUaName(
                nameEntityRequest.getEnName(),
                nameEntityRequest.getUaName()
        );
    }

    @Override
    protected void updateEntity(T entity, UpdateRequest request) {
        var nameEntityRequest = (NameEntityUpdateRequest) request;

        if(!nameEntityRequest.getEnName().isBlank()) {
            entity.setEnName(nameEntityRequest.getEnName());
        }
        if(!nameEntityRequest.getUaName().isBlank()) {
            entity.setUaName(nameEntityRequest.getUaName());
        }
    }
}
