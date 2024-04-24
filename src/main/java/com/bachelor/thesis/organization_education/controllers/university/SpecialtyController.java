package com.bachelor.thesis.organization_education.controllers.university;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import com.bachelor.thesis.organization_education.controllers.ResourceController;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.responces.university.UniversityResponse;
import com.bachelor.thesis.organization_education.requests.find.university.SpecialtyFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.SpecialtyService;
import com.bachelor.thesis.organization_education.requests.insert.university.SpecialtyInsertRequest;
import com.bachelor.thesis.organization_education.requests.update.university.SpecialtyUpdateRequest;

@RestController
@RequestMapping("/specialty")
public class SpecialtyController extends ResourceController<SpecialtyService> {
    @Autowired
    public SpecialtyController(SpecialtyService service) {
        super(service);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Response> add(@RequestBody @Valid SpecialtyInsertRequest request) {
        var response = service.addResource(request)
                .getResponse();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> get(@RequestBody @Valid SpecialtyFindRequest request) {
        return super.get(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UniversityResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid SpecialtyUpdateRequest request
    ) {
        return super.updateEntity(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        return super.deactivate(id);
    }
}
