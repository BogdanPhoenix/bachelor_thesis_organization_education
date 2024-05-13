package com.bachelor.thesis.organization_education.services.implementations.crud;

import lombok.NonNull;
import org.springframework.util.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.context.ApplicationContext;
import com.bachelor.thesis.organization_education.dto.Lecturer;
import org.springframework.security.core.context.SecurityContextHolder;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.exceptions.UnauthorizedException;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.*;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Supplier;
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
    public List<Response> addValue(@NonNull Collection<? extends InsertRequest> requests) throws DuplicateException, NullPointerException {
        return requests.stream()
                .map(this::addValue)
                .map(BaseTableInfo::getResponse)
                .toList();
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
    public T updateValue(@NonNull UUID id, @NonNull UpdateRequest request) throws NotFindEntityInDataBaseException {
        var entity = findValueById(id);
        validateOwner(entity);
        updateEntity(entity, request);
        entity.setUpdateDate(LocalDateTime.now());
        return repository.save(entity);
    }

    protected void validateDuplicate(UUID entityId, FindRequest request) throws DuplicateException {
        if(isDuplicate(entityId, request)){
            messageDuplicate(request);
        }
    }

    boolean isDuplicate(UUID entityId, FindRequest request) {
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
        repository.findById(id)
                .ifPresent(e -> updateEnabled(e, true));
    }

    @Override
    public void deactivate(@NonNull UUID id) {
        repository.findById(id)
                .ifPresent(e -> {
                    validateOwner(e);
                    deactivateEntity(e);
                });
    }

    protected void deactivateEntity(T entity) {
        selectedForDeactivateChild(entity);
        updateEnabled(entity, false);
    }

    protected void validateOwner(T entity) {
        if(!isOwner(entity)) {
            throw new UnauthorizedException("You cannot interact with this entity because you aren`t its owner.");
        }
    }

    private boolean isOwner(T entity) {
        var uuid = getAuthenticationUUID();
        return checkOwner(entity, uuid);
    }

    protected UUID getAuthenticationUUID() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return UUID.fromString(auth.getName());
    }

    protected boolean checkOwner(T entity, UUID userId) {
        return true;
    }

    protected void checkLecturer(Lecturer lecturer, UUID authId) {
        if(!lecturer.getId().equals(authId)) {
            throw new UnauthorizedException("You don`t lecturer in the selected group, so you cannot add data to it.");
        }
    }

    protected void selectedForDeactivateChild(T entity) {
        //You need to override only in classes where it is required.
    }

    protected void updateEnabled(T entity, boolean value) {
        entity.setEnabled(value);
        entity.setUpdateDate(LocalDateTime.now());
        repository.save(entity);
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
    public Page<Response> getAll(Pageable pageable) {
        return repository.findAllActiveEntities(pageable)
                .map(BaseTableInfo::getResponse);
    }

    @Override
    public void deleteValue(@NonNull UUID id) {
        var entity = repository.findById(id);
        entity.ifPresent(repository::delete);
    }

    protected <B extends BaseTableInfo, C extends CrudServiceAbstract<B, ?>> void deactivatedChild(Collection<B> collection, Class<C> serviceClass) {
        var service = getBeanByClass(serviceClass);
        collection.forEach(service::deactivateEntity);
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

    protected <R> void updateIfPresent(Supplier<R> value, Consumer<R> setterFunction) {
        Optional.ofNullable(value.get())
                .filter(val -> !ObjectUtils.isEmpty(val))
                .ifPresent(setterFunction);
    }

    protected abstract T createEntity(InsertRequest request);
    protected abstract List<T> findAllEntitiesByRequest(@NonNull FindRequest request);
    protected abstract void updateEntity(T entity, UpdateRequest request);
}
