package com.bachelor.thesis.organization_education.controllers.university;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import com.bachelor.thesis.organization_education.controllers.ResourceController;
import com.bachelor.thesis.organization_education.responces.university.UniversityResponse;
import com.bachelor.thesis.organization_education.requests.find.university.UniversityFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityService;
import com.bachelor.thesis.organization_education.requests.insert.university.UniversityInsertRequest;
import com.bachelor.thesis.organization_education.requests.update.university.UniversityUpdateRequest;

import java.security.Principal;

@RestController
@RequestMapping("/university")
public class UniversityController extends ResourceController<UniversityService> {
    @Autowired
    public UniversityController(UniversityService service) {
        super(service);
    }

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @PostMapping
    public ResponseEntity<UniversityResponse> add(
            @RequestBody @Valid UniversityInsertRequest request,
            Principal principal
    ) {
        var response = service
                .addResource(request, principal.getName())
                .getResponse();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<UniversityResponse> get(@RequestBody @Valid UniversityFindRequest request) {
        return super.get(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UniversityResponse> get(@PathVariable Long id) {
        return super.get(id);
    }

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UniversityResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid UniversityUpdateRequest request
    ) {
        return super.updateEntity(id, request);
    }

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        return super.deactivate(id);
    }
}
