package com.bachelor.thesis.organization_education.services.implementations.crud;

import lombok.NonNull;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.requests.general.abstracts.Request;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import java.util.Set;
import java.util.Objects;
import java.util.Optional;
import java.util.Collection;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.function.Predicate;

import static com.bachelor.thesis.organization_education.services.implementations.tools.ExceptionTools.throwRuntimeException;

/**
 * An abstract class that contains an implementation of CRUD functionality common to all service classes.
 * @param <T> type of the entity class for which CRUD is implemented.
 * @param <J> the type of entity repository that manages access to and interconnection with the entity.
 */
public abstract class CrudServiceAbstract<T extends BaseTableInfo, J extends JpaRepository<T, Long>> implements CrudService {
    protected final String tableName;
    protected final J repository;
    protected final ApplicationContext context;

    protected CrudServiceAbstract(J repository, String tableName) {
        this(repository, tableName, null);
    }

    protected CrudServiceAbstract(J repository, String tableName, ApplicationContext context) {
        this.repository = repository;
        this.tableName = tableName;
        this.context = context;
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
        return findEntityByRequest(request)
                .isPresent();
    }

    @Override
    public T updateValue(@NonNull Long id, @NonNull UpdateRequest request) throws DuplicateException, NotFindEntityInDataBaseException {
        validateDuplicate(request.getFindRequest(), id);
        var entity = findValueById(id);

        return updateValue(entity, request);
    }

    protected T updateValue(T entity, UpdateRequest request) {
        updateEntity(entity, request);
        entity.setUpdateDate(LocalDateTime.now());
        return repository.save(entity);
    }

    private void validateDuplicate(FindRequest request, Long entityId) throws DuplicateException {
        if(!request.skip() && isDuplicate(request, entityId)){
            messageDuplicate(request);
        }
    }

    boolean isDuplicate(FindRequest request, Long entityId) {
        return findEntityByRequest(request)
                .map(entity -> !Objects.equals(entity.getId(), entityId))
                .orElse(false);
    }

    private void messageDuplicate(FindRequest request) throws DuplicateException {
        var message = String.format("The \"%s\" entity is already present in the \"%s\" table.", request, tableName);
        throwRuntimeException(message, DuplicateException::new);
    }

    @Override
    public void activate(@NonNull Long id) throws NotFindEntityInDataBaseException {
        var entity = findEntityById(id);
        updateEnabled(entity, true);
    }

    @Override
    public void deactivate(@NonNull Long id) throws NotFindEntityInDataBaseException {
        selectedForDeactivateChild(id);
        var entity = findEntityById(id);
        updateEnabled(entity, false);
    }

    protected void updateEnabled(T entity, boolean value) throws NotFindEntityInDataBaseException {
        entity.setEnabled(value);
        entity.setUpdateDate(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    public T getValue(@NonNull FindRequest request) throws NotFindEntityInDataBaseException {
        return getEntityAndFilter(request, BaseTableInfo::isEnabled);
    }

    @Override
    public BaseTableInfo getValue(@NonNull Long id) throws NotFindEntityInDataBaseException {
        return findValueById(id);
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
    public void deleteValue(@NonNull Long id) throws NotFindEntityInDataBaseException {
        var entity = findEntityById(id);
        repository.delete(entity);
    }

    protected T getEntity(FindRequest request) throws NotFindEntityInDataBaseException {
        return getEntityAndFilter(request, filter -> true);
    }

    private T getEntityAndFilter(FindRequest request, Predicate<? super T> filterPredicate) throws NotFindEntityInDataBaseException {
        return findEntityByRequest(request)
                .filter(filterPredicate)
                .orElseThrow(() -> new NotFindEntityInDataBaseException("The query failed to find an entity in the table: " + tableName));
    }

    protected <B extends BaseTableInfo, C extends CrudService> void deactivatedChild(Collection<B> collection, Class<C> serviceClass) {
        var service = getBeanByClass(serviceClass);
        collection.forEach(
                entity -> service.deactivate(entity.getId())
        );
    }

    private <B extends CrudService> B getBeanByClass(Class<B> clazz) {
        return context.getBean(clazz);
    }

    protected T findEntityById(Long id) throws NotFindEntityInDataBaseException {
        return findByIdAndFilter(id, filter -> true);
    }

    T findValueById(Long id) throws NotFindEntityInDataBaseException {
        return findByIdAndFilter(id, BaseTableInfo::isEnabled);
    }

    private T findByIdAndFilter(Long id, Predicate<? super T> filterPredicate) throws NotFindEntityInDataBaseException {
        return repository
                .findById(id)
                .filter(filterPredicate)
                .orElseThrow(() -> {
                            var message = String.format("Unable to find an entity in the \"%s\" table using the specified identifier: %d.", tableName, id);
                            return new NotFindEntityInDataBaseException(message);
                        }
                );
    }

    protected abstract T createEntity(Request request);
    protected abstract Optional<T> findEntityByRequest(@NonNull FindRequest request);
    protected abstract void updateEntity(T entity, UpdateRequest request);
    protected abstract void selectedForDeactivateChild(Long id);
}
