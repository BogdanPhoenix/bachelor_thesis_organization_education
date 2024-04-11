package com.bachelor.thesis.organization_education.controllers.university;

import com.bachelor.thesis.organization_education.requests.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.abstract_type.Request;
import com.bachelor.thesis.organization_education.requests.university.UniversityFindRequest;
import com.bachelor.thesis.organization_education.requests.university.UniversityInsertRequest;
import com.bachelor.thesis.organization_education.requests.university.UniversityRequest;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.responces.university.UniversityResponse;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;
import java.util.function.Consumer;

@RestController
@RequiredArgsConstructor
@RequestMapping("/university")
public class UniversityController {
    private final UniversityService service;

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @PostMapping
    public ResponseEntity<UniversityResponse> add(
            @RequestBody @Valid UniversityInsertRequest request,
            BindingResult bindingResult,
            Principal principal
    ) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        var response = service.addResource(request, principal.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<UniversityResponse> get(
            @RequestBody @Valid UniversityFindRequest request,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        var response = (UniversityResponse) service.getValue(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public Set<Response> getAll() {
        return service.getAll();
    }

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @PutMapping
    public ResponseEntity<UniversityResponse> update(
            @RequestBody @Valid UpdateRequest<UniversityResponse, UniversityRequest> request,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        var response = (UniversityResponse) service.updateValue(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> deactivate(
            @RequestBody @Valid UniversityFindRequest request,
            BindingResult bindingResult
    ) {
        return processingRequest(request, bindingResult, service::disable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(
            @RequestBody @Valid UniversityFindRequest request,
            BindingResult bindingResult
    ) {
        return processingRequest(request, bindingResult, service::deleteValue);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/activate")
    public ResponseEntity<?> activate(
            @RequestBody @Valid UniversityFindRequest request,
            BindingResult bindingResult
    ) {
        return processingRequest(request, bindingResult, service::enable);
    }

    private ResponseEntity<?> processingRequest(
            Request request,
            BindingResult bindingResult,
            Consumer<Request> method
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
