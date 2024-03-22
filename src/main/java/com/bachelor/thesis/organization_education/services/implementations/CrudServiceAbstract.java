package com.bachelor.thesis.organization_education.services.implementations;

import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.requests.abstract_type.Request;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.services.interfaces.CrudService;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.bachelor.thesis.organization_education.services.implementations.tools.ExceptionTools.throwRuntimeException;

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

    private void validateDuplicate(@NonNull Request request) throws DuplicateException {
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
                .orElseThrow(() -> new NotFindEntityInDataBaseException("User could not be found"));
        entity.setEnabled(value);
        repository.save(entity);
    }

    protected abstract T createEntity(@NonNull Request request);
    protected abstract Optional<T> findEntity(@NonNull Request request);
}
