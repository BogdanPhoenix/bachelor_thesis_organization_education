package com.bachelor.thesis.organization_education.controllers.university;

import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.requests.update.UpdateData;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.services.interfaces.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class ResourceController<T extends CrudService> {
    protected final T service;

    protected ResourceController(T service) {
        this.service = service;
    }

    @SuppressWarnings("unchecked")
    public <I extends FindRequest, O extends Response> ResponseEntity<O> get(I request, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        var response = (O) service.getValue(request)
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
    public <I extends UpdateRequest, O extends Response> ResponseEntity<O> updateEntity(UpdateData<I> request, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        var response = (O) service.updateValue(request)
                .getResponse();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    public <I extends FindRequest> ResponseEntity<Void> deactivate(I request, BindingResult bindingResult) {
        return processingRequest(request, bindingResult, service::disable);
    }

    public <I extends FindRequest> ResponseEntity<Void> delete(I request, BindingResult bindingResult) {
        return processingRequest(request, bindingResult, service::deleteValue);
    }

    public <I extends FindRequest> ResponseEntity<Void> activate(I request, BindingResult bindingResult) {
        return processingRequest(request, bindingResult, service::enable);
    }

    private ResponseEntity<Void> processingRequest(
            FindRequest request,
            BindingResult bindingResult,
            Consumer<FindRequest> method
    ) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        method.accept(request);
        return ResponseEntity.ok().build();
    }
}
