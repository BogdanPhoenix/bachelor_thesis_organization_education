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

    protected  <I extends FindRequest> ResponseEntity<Response> get(I request) {
        var response = service.getValue(request)
                .getResponse();

        return ResponseEntity.ok(response);
    }

    protected ResponseEntity<Response> get(@PathVariable Long id) {
        var response = service.getValue(id)
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

    protected <I extends UpdateRequest> ResponseEntity<Response> updateEntity(Long id, I request) {
        var response = service.updateValue(id, request)
                .getResponse();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    protected ResponseEntity<Void> deactivate(Long id) {
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
