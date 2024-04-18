package com.bachelor.thesis.organization_education.controllers.university;

import com.bachelor.thesis.organization_education.requests.insert.university.FacultyInsertRequest;
import com.bachelor.thesis.organization_education.requests.find.university.FacultyFindRequest;
import com.bachelor.thesis.organization_education.requests.update.university.FacultyUpdateRequest;
import com.bachelor.thesis.organization_education.responces.university.FacultyResponse;
import com.bachelor.thesis.organization_education.services.interfaces.university.FacultyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/faculty")
public class FacultyController extends ResourceController<FacultyService>{
    @Autowired
    public FacultyController(FacultyService service) {
        super(service);
    }

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @PostMapping
    public ResponseEntity<FacultyResponse> add(
            @RequestBody @Valid FacultyInsertRequest request,
            BindingResult bindingResult,
            Principal principal
    ) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        var response = service.addResource(request, principal.getName())
                .getResponse();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<FacultyResponse> get(
            @RequestBody @Valid FacultyFindRequest request,
            BindingResult bindingResult
    ) {
        return super.get(request, bindingResult);
    }

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<FacultyResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid FacultyUpdateRequest request,
            BindingResult bindingResult
    ) {
        return super.updateEntity(id, request, bindingResult);
    }

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> deactivate(
            @RequestBody @Valid FacultyFindRequest request,
            BindingResult bindingResult
    ) {
        return super.deactivate(request, bindingResult);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(
            @RequestBody @Valid FacultyFindRequest request,
            BindingResult bindingResult
    ) {
        return super.delete(request, bindingResult);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/activate")
    public ResponseEntity<?> activate(
            @RequestBody @Valid FacultyFindRequest request,
            BindingResult bindingResult
    ) {
        return super.activate(request, bindingResult);
    }
}
