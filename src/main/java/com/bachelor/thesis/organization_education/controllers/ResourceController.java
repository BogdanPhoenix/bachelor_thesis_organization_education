package com.bachelor.thesis.organization_education.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;

import java.util.UUID;

/**
 * A base controller for handling RESTful endpoints related to a resource.
 * Supports CRUD operations and activation/deactivation of entities.
 * @param <T> The service type extending CrudService for the resource.
 */
public abstract class ResourceController<T extends CrudService> {
    protected final T service;

    protected ResourceController(T service) {
        this.service = service;
    }

    protected  <I extends FindRequest> ResponseEntity<Response> get(I request) {
        var response = service.getValue(request)
                .getResponse();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    protected ResponseEntity<Response> get(@PathVariable UUID id) {
        var response = service.getValue(id)
                .getResponse();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public Page<Response> getAll(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        return service.getAll(pageable)
                .map(BaseTableInfo::getResponse);
    }

    protected <I extends UpdateRequest> ResponseEntity<Response> updateEntity(UUID id, I request) {
        var response = service.updateValue(id, request)
                .getResponse();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        service.deactivate(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteValue(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<Void> activate(@PathVariable UUID id) {
        service.activate(id);
        return ResponseEntity.ok().build();
    }
}
