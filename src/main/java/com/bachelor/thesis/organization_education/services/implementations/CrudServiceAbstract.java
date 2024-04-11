package com.bachelor.thesis.organization_education.services.implementations;

import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.requests.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.abstract_type.Request;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.services.interfaces.CrudService;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bachelor.thesis.organization_education.services.implementations.tools.ExceptionTools.throwRuntimeException;

/**
 * An abstract class that contains an implementation of CRUD functionality common to all service classes.
 * @param <T> type of the entity class for which CRUD is implemented.
 * @param <J> the type of entity repository that manages access to and interconnection with the entity.
 */
public abstract class CrudServiceAbstract<T extends BaseTableInfo, J extends JpaRepository<T, Long>> implements CrudService {
    protected final String tableName;
    protected final J repository;

    protected CrudServiceAbstract(J repository, String tableName) {
        this.repository = repository;
        this.tableName = tableName;
    }

    @Override
    public Response addValue(@NonNull Request request) throws DuplicateException, NullPointerException {
        validateDuplicate(request);

        var newEntity = createEntity(request);
        var result = repository.save(newEntity);

        return result.getResponse();
    }

    @Override
    public Response updateValue(@NonNull UpdateRequest<? extends Response, ? extends Request> request) throws DuplicateException, NotFindEntityInDataBaseException {
        validateDuplicate(request.getNewValue());

        var id = request.getOldValue().getId();
        var entity = findById(id);

        updateEntity(entity, request.getNewValue());
        entity.setUpdateDate(LocalDateTime.now());
        var result = repository.save(entity);

        return result.getResponse();
    }

    private void validateDuplicate(Request request) throws DuplicateException {
        if(!isDuplicate(request)){
            return;
        }

        var message = String.format("The \"%s\" entity is already present in the \"%s\" table.", request, tableName);
        throwRuntimeException(message, DuplicateException::new);
    }

    boolean isDuplicate(Request request){
        return !request.isEmpty() && findEntity(request).isPresent();
    }

    @Override
    public void enable(@NonNull Request request) throws NotFindEntityInDataBaseException {
        updateEnabled(request, true);
    }

    @Override
    public void disable(@NonNull Request request) throws NotFindEntityInDataBaseException {
        updateEnabled(request, false);
    }

    private void updateEnabled(Request request, boolean value) throws NotFindEntityInDataBaseException {
        var entity = findEntity(request)
                .orElseThrow(() -> new NotFindEntityInDataBaseException("The query failed to find an entity in the table: " + tableName));

        entity.setEnabled(value);
        entity.setUpdateDate(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    public Response getValue(@NonNull Request request) throws NotFindEntityInDataBaseException {
        return getEntity(request)
                .getResponse();
    }

    @Override
    public Set<Response> getAll() {
        return findAll()
                .stream()
                .map(BaseTableInfo::getResponse)
                .collect(Collectors.toSet());
    }

    private Set<T> findAll() {
        return repository
                .findAll()
                .stream()
                .filter(BaseTableInfo::isEnabled)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteValue(@NonNull Request request) throws NotFindEntityInDataBaseException {
        var entity = findEntity(request)
                .orElseThrow();
        repository.delete(entity);
    }

    T findById(Long id) throws NotFindEntityInDataBaseException {
        return repository
                .findById(id)
                .filter(BaseTableInfo::isEnabled)
                .orElseThrow(() -> {
                            var message = String.format("Unable to find an entity in the \"%s\" table using the specified identifier: %d.", tableName, id);
                            return new NotFindEntityInDataBaseException(message);
                        }
                );
    }

    protected abstract T createEntity(@NonNull Request request);
    protected abstract T getEntity(@NonNull Request request) throws NotFindEntityInDataBaseException;
    protected abstract Optional<T> findEntity(@NonNull Request request);
    protected abstract void updateEntity(T entity, Request request);
}
