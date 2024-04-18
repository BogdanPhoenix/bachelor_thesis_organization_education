package com.bachelor.thesis.organization_education.controllers.university;

import com.bachelor.thesis.organization_education.requests.find.university.UniversityFindRequest;
import com.bachelor.thesis.organization_education.requests.insert.university.UniversityInsertRequest;
import com.bachelor.thesis.organization_education.requests.update.university.UniversityUpdateRequest;
import com.bachelor.thesis.organization_education.responces.university.UniversityResponse;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/university")
public class UniversityController extends ResourceController<UniversityService>{
    @Autowired
    public UniversityController(UniversityService service) {
        super(service);
    }

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

        var response = service
                .addResource(request, principal.getName())
                .getResponse();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<UniversityResponse> get(
            @RequestBody @Valid UniversityFindRequest request,
            BindingResult bindingResult
    ) {
        return super.get(request, bindingResult);
    }

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UniversityResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid UniversityUpdateRequest request,
            BindingResult bindingResult
    ) {
        return super.updateEntity(id, request, bindingResult);
    }

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deactivate(
            @RequestBody @Valid UniversityFindRequest request,
            BindingResult bindingResult
    ) {
        return super.deactivate(request, bindingResult);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(
            @RequestBody @Valid UniversityFindRequest request,
            BindingResult bindingResult
    ) {
        return super.delete(request, bindingResult);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/activate")
    public ResponseEntity<Void> activate(
            @RequestBody @Valid UniversityFindRequest request,
            BindingResult bindingResult
    ) {
        return super.activate(request, bindingResult);
    }
}
