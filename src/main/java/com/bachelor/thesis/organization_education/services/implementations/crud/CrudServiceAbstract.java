package com.bachelor.thesis.organization_education.services.implementations.crud;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.context.ApplicationContext;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.*;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import static com.bachelor.thesis.organization_education.services.implementations.tools.ExceptionTools.throwRuntimeException;

/**
 * An abstract class that contains an implementation of CRUD functionality common to all service classes.
 * @param <T> type of the entity class for which CRUD is implemented.
 * @param <J> the type of entity repository that manages access to and interconnection with the entity.
 */
public abstract class CrudServiceAbstract<T extends BaseTableInfo, J extends BaseTableInfoRepository<T>> implements CrudService {
    protected final String tableName;
    protected final J repository;
    protected final ApplicationContext context;

    protected CrudServiceAbstract(J repository, String tableName, ApplicationContext context) {
        this.repository = repository;
        this.tableName = tableName;
        this.context = context;
    }

    @Override
    public T addValue(@NonNull InsertRequest request) throws DuplicateException, NullPointerException {
        validateDuplicate(request.getFindRequest());
        var newEntity = createEntity(request);
        return repository.save(newEntity);
    }

    protected void validateDuplicate(FindRequest request) throws DuplicateException {
        if(isDuplicate(request)){
            messageDuplicate(request);
        }
    }

    boolean isDuplicate(FindRequest request){
        return !findAllEntitiesByRequest(request).isEmpty();
    }

    @Override
    public T updateValue(@NonNull UUID id, @NonNull UpdateRequest request) throws DuplicateException, NotFindEntityInDataBaseException {
        validateDuplicate(request.getFindRequest(), id);
        var entity = findValueById(id);

        return updateValue(entity, request);
    }

    protected T updateValue(T entity, UpdateRequest request) {
        updateEntity(entity, request);
        entity.setUpdateDate(LocalDateTime.now());
        return repository.save(entity);
    }

    private void validateDuplicate(FindRequest request, UUID entityId) throws DuplicateException {
        if(isDuplicate(request, entityId)){
            messageDuplicate(request);
        }
    }

    boolean isDuplicate(FindRequest request, UUID entityId) {
        var entities = findAllEntitiesByRequest(request);

        if(entities.size() > 1) {
            return true;
        }

        return entities.stream()
                .findFirst()
                .map(entity -> !Objects.equals(entity.getId(), entityId))
                .orElse(false);
    }

    private void messageDuplicate(FindRequest request) throws DuplicateException {
        var message = String.format("The \"%s\" entity is already present in the \"%s\" table.", request, tableName);
        throwRuntimeException(message, DuplicateException::new);
    }

    @Override
    public void activate(@NonNull UUID id) {
        updateEnabled(id, true);
    }

    @Override
    public void deactivate(@NonNull UUID id) {
        selectedForDeactivateChild(id);
        updateEnabled(id, false);
    }

    protected void selectedForDeactivateChild(UUID id) {
        //You need to override only in classes where it is required.
    }

    protected void updateEnabled(UUID id, boolean value) {
        var entity = repository.findById(id);
        entity.ifPresent(e -> {
            e.setEnabled(value);
            e.setUpdateDate(LocalDateTime.now());
            repository.save(e);
        });
    }

    @Override
    public T getValue(@NonNull FindRequest request) throws NotFindEntityInDataBaseException {
        return findAllEntitiesByRequest(request)
                .stream()
                .filter(BaseTableInfo::isEnabled)
                .findFirst()
                .orElseThrow(() -> {
                    var errorMessage = String.format("No entities were found for the query: %s could not find any entities in the table: %s.", request, tableName);
                    return new NotFindEntityInDataBaseException(errorMessage);
                });
    }

    @Override
    public BaseTableInfo getValue(@NonNull UUID id) throws NotFindEntityInDataBaseException {
        return findValueById(id);
    }

    @Override
    public Page<BaseTableInfo> getAll(Pageable pageable) {
        return repository.findAllActiveEntities(pageable);
    }

    @Override
    public void deleteValue(@NonNull UUID id) {
        var entity = repository.findById(id);
        entity.ifPresent(repository::delete);
    }

    protected <B extends BaseTableInfo, C extends CrudService> void deactivatedChild(Collection<B> collection, Class<C> serviceClass) {
        var service = getBeanByClass(serviceClass);
        collection.forEach(
                entity -> service.deactivate(entity.getId())
        );
    }

    @SuppressWarnings("unchecked")
    protected <B extends BaseTableInfo, C extends CrudService> B getValue(B request, Class<C> serviceClass) {
        var service = getBeanByClass(serviceClass);
        return (B) service.getValue(request.getId());
    }

    protected  <B extends CrudService> B getBeanByClass(Class<B> clazz) {
        return context.getBean(clazz);
    }

    protected T findEntityById(UUID id) throws NotFindEntityInDataBaseException {
        return findByIdAndFilter(id, filter -> true);
    }

    protected T findValueById(UUID id) throws NotFindEntityInDataBaseException {
        return findByIdAndFilter(id, BaseTableInfo::isEnabled);
    }

    private T findByIdAndFilter(UUID id, Predicate<? super T> filterPredicate) throws NotFindEntityInDataBaseException {
        return repository
                .findById(id)
                .filter(filterPredicate)
                .orElseThrow(() -> {
                            var message = String.format("Unable to find an entity in the \"%s\" table using the specified identifier: %s.", tableName, id);
                            return new NotFindEntityInDataBaseException(message);
                        }
                );
    }

    protected abstract T createEntity(InsertRequest request);
    protected abstract List<T> findAllEntitiesByRequest(@NonNull FindRequest request);
    protected abstract void updateEntity(T entity, UpdateRequest request);
}
