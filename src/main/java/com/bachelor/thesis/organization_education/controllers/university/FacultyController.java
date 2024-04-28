package com.bachelor.thesis.organization_education.controllers.university;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.controllers.ResourceController;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.university.FacultyRequest;
import com.bachelor.thesis.organization_education.requests.find.university.FacultyFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.FacultyService;

import java.util.UUID;
import java.security.Principal;

@RestController
@RequestMapping("/faculties")
@Validated
public class FacultyController extends ResourceController<FacultyService> {
    @Autowired
    public FacultyController(FacultyService service) {
        super(service);
    }

    @PostMapping
    public ResponseEntity<Response> add(
            @Validated(InsertRequest.class) @RequestBody FacultyRequest request,
            Principal principal
    ) {
        var response = service.addResource(request, principal.getName())
                .getResponse();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> get(@Validated @RequestBody FacultyFindRequest request) {
        return super.get(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @PathVariable UUID id,
            @Validated(UpdateRequest.class) @RequestBody FacultyRequest request
    ) {
        return super.updateEntity(id, request);
    }
}
