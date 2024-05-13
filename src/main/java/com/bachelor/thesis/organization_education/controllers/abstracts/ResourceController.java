package com.bachelor.thesis.organization_education.controllers.abstracts;

import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import com.bachelor.thesis.organization_education.requests.general.ListRequest;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

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

    protected ResponseEntity<List<Response>> addValue(ListRequest<? extends InsertRequest> requests) {
        var response = service.addValue(requests.collection());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    protected ResponseEntity<Response> addValue(@Validated(InsertRequest.class) @RequestBody InsertRequest request) {
        var response = service.addValue(request)
                .getResponse();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
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
        return service.getAll(pageable);
    }

    protected ResponseEntity<Page<Response>> getAllByUser(
            int pageNumber,
            int pageSize,
            Function<Pageable, Page<Response>> function
    ) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var responses = function.apply(pageable);
        return ResponseEntity.ok(responses);
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
