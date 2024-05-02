package com.bachelor.thesis.organization_education.controllers.university;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.controllers.ResourceController;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.general.university.AudienceRequest;
import com.bachelor.thesis.organization_education.requests.find.university.AudienceFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.AudienceService;

import java.util.UUID;
import java.security.Principal;

@RestController
@RequestMapping("/audiences")
@Validated
public class AudienceController extends ResourceController<AudienceService> {
    @Autowired
    public AudienceController(AudienceService service) {
        super(service);
    }

    @PostMapping
    public ResponseEntity<Response> add(
            @Validated(InsertRequest.class) @RequestBody AudienceRequest request,
            Principal principal
    ) {
        var response = service.addResource(request, principal.getName())
                .getResponse();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> get(@Validated @RequestBody AudienceFindRequest request) {
        return super.get(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @PathVariable UUID id,
            @Validated(UpdateRequest.class) @RequestBody AudienceRequest request
    ) {
        var response = service.updateValue(id, request)
                .getResponse();

        return ResponseEntity.ok(response);
    }
}
