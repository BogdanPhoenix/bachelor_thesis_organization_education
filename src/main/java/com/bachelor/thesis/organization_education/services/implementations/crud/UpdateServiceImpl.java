package com.bachelor.thesis.organization_education.services.implementations.crud;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.exceptions.UnauthorizedException;
import com.bachelor.thesis.organization_education.services.interfaces.crud.UpdateService;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;

@Transactional
@RequiredArgsConstructor
public class UpdateServiceImpl<T extends BaseTableInfo, J extends BaseTableInfoRepository<T>> implements UpdateService {
    private final J repository;
    private final BiPredicate<T, UUID> checkOwner;
    private final Function<UUID, T> findValueById;
    private final BiConsumer<T, UpdateRequest> updateRequest;

    @Override
    public T updateValue(@NonNull UUID id, @NonNull UpdateRequest request) throws NotFindEntityInDataBaseException {
        T entity = findValueById.apply(id);
        validateOwner(entity);
        updateRequest.accept(entity, request);
        entity.setUpdateDate(LocalDateTime.now());
        return repository.save(entity);
    }

    private void validateOwner(T entity) {
        if(!isOwner(entity)) {
            throw new UnauthorizedException("You cannot interact with this entity because you aren`t its owner.");
        }
    }

    private boolean isOwner(T entity) {
        var uuid = getAuthenticationUUID();
        return checkOwner.test(entity, uuid);
    }

    private UUID getAuthenticationUUID() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return UUID.fromString(auth.getName());
    }
}
