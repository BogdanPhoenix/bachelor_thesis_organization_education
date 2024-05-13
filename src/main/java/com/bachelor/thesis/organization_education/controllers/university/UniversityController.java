package com.bachelor.thesis.organization_education.controllers.university;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.controllers.abstracts.ResourceController;
import com.bachelor.thesis.organization_education.requests.general.university.UniversityRequest;
import com.bachelor.thesis.organization_education.requests.find.university.UniversityFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityService;

import java.util.UUID;

@RestController
@RequestMapping("/universities")
@Validated
public class UniversityController extends ResourceController<UniversityService> {
    @Autowired
    public UniversityController(UniversityService service) {
        super(service);
    }

    @PostMapping
    public ResponseEntity<Response> add(@Validated(InsertRequest.class) @RequestBody UniversityRequest request) {
        return super.addValue(request);
    }

    @GetMapping
    public ResponseEntity<Response> get(@Validated @RequestBody UniversityFindRequest request) {
        return super.get(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @PathVariable UUID id,
            @Validated(UpdateRequest.class) @RequestBody UniversityRequest request
    ) {
       return super.updateEntity(id, request);
    }
}
