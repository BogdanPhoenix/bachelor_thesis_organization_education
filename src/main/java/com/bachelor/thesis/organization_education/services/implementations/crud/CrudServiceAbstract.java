package com.bachelor.thesis.organization_education.services.implementations.crud;

import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.requests.update.UpdateData;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.Request;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.services.interfaces.CrudService;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Objects;
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
    public T addValue(@NonNull Request request) throws DuplicateException, NullPointerException {
        validateDuplicate(request.getFindRequest());

        var newEntity = createEntity(request);
        return repository.save(newEntity);
    }

    private void validateDuplicate(FindRequest request) throws DuplicateException {
        if(isDuplicate(request)){
            messageDuplicate(request);
        }
    }

    boolean isDuplicate(FindRequest request){
        return findEntity(request)
                .isPresent();
    }

    @Override
    public T updateValue(@NonNull UpdateData<? extends UpdateRequest> request) throws DuplicateException, NotFindEntityInDataBaseException {
        var updateRequest = request.getNewValue();
        validateDuplicate(updateRequest.getFindRequest(), request.getId());
        var entity = findById(request.getId());

        updateEntity(entity, updateRequest);
        entity.setUpdateDate(LocalDateTime.now());
        return repository.save(entity);
    }

    private void validateDuplicate(FindRequest request, Long entityId) throws DuplicateException {
        if(!request.skip() && isDuplicate(request, entityId)){
            messageDuplicate(request);
        }
    }

    private void messageDuplicate(FindRequest request) throws DuplicateException {
        var message = String.format("The \"%s\" entity is already present in the \"%s\" table.", request, tableName);
        throwRuntimeException(message, DuplicateException::new);
    }

    boolean isDuplicate(FindRequest request, Long entityId) {
        return findEntity(request)
                .map(entity -> !Objects.equals(entity.getId(), entityId))
                .orElse(false);
    }

    @Override
    public void enable(@NonNull FindRequest request) throws NotFindEntityInDataBaseException {
        updateEnabled(request, true);
    }

    @Override
    public void disable(@NonNull FindRequest request) throws NotFindEntityInDataBaseException {
        updateEnabled(request, false);
    }

    private void updateEnabled(FindRequest request, boolean value) throws NotFindEntityInDataBaseException {
        var entity = getEntity(request);

        entity.setEnabled(value);
        entity.setUpdateDate(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    public Set<BaseTableInfo> getAll() {
        return repository
                .findAll()
                .stream()
                .filter(BaseTableInfo::isEnabled)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteValue(@NonNull FindRequest request) throws NotFindEntityInDataBaseException {
        var entity = getEntity(request);
        repository.delete(entity);
    }

    T getEntity(FindRequest request) {
        return findEntity(request)
                .orElseThrow(() -> new NotFindEntityInDataBaseException("The query failed to find an entity in the table: " + tableName));
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
    protected abstract Optional<T> findEntity(@NonNull FindRequest request);
    protected abstract void updateEntity(T entity, UpdateRequest request);
}
