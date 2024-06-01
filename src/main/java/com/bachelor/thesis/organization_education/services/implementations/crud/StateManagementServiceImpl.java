package com.bachelor.thesis.organization_education.services.implementations.crud;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.exceptions.UnauthorizedException;
import com.bachelor.thesis.organization_education.repositories.abstracts.BaseTableInfoRepository;
import com.bachelor.thesis.organization_education.services.interfaces.crud.StateManagementService;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.BiPredicate;

@Transactional
@RequiredArgsConstructor
public class StateManagementServiceImpl<T extends BaseTableInfo, J extends BaseTableInfoRepository<T>> implements StateManagementService {
    private final J repository;
    private final Consumer<T> deactivateChild;
    private final BiPredicate<T, UUID> checkOwner;

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

    private void deactivateEntity(T entity) {
        deactivateChild.accept(entity);
        updateEnabled(entity, false);
    }

    private void updateEnabled(T entity, boolean value) {
        entity.setEnabled(value);
        entity.setUpdateDate(LocalDateTime.now());
        repository.save(entity);
    }
}
