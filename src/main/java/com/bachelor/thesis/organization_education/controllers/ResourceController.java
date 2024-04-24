package com.bachelor.thesis.organization_education.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class ResourceController<T extends CrudService> {
    protected final T service;

    protected ResourceController(T service) {
        this.service = service;
    }

    @SuppressWarnings("unchecked")
    public <I extends FindRequest, O extends Response> ResponseEntity<O> get(I request) {
        var response = (O) service.getValue(request)
                .getResponse();

        return ResponseEntity.ok(response);
    }

    @SuppressWarnings("unchecked")
    public <O extends Response> ResponseEntity<O> get(Long id) {
        var response = (O) service.getValue(id)
                .getResponse();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public Set<Response> getAll() {
        return service.getAll()
                .stream()
                .map(BaseTableInfo::getResponse)
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    public <I extends UpdateRequest, O extends Response> ResponseEntity<O> updateEntity(Long id, I request) {
        var response = (O) service.updateValue(id, request)
                .getResponse();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    public ResponseEntity<Void> deactivate(Long id) {
        service.deactivate(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteValue(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/activate/{id}")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        service.activate(id);
        return ResponseEntity.ok().build();
    }
}
