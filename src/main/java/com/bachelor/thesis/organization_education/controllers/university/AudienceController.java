package com.bachelor.thesis.organization_education.controllers.university;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.requests.general.ListRequest;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.controllers.abstracts.ResourceController;
import com.bachelor.thesis.organization_education.requests.general.university.AudienceRequest;
import com.bachelor.thesis.organization_education.requests.find.university.AudienceFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.AudienceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/audiences")
@Validated
public class AudienceController extends ResourceController<AudienceService> {
    @Autowired
    public AudienceController(AudienceService service) {
        super(service);
    }

    @PostMapping("/stream")
    public ResponseEntity<List<Response>> addStream(@Valid @RequestBody ListRequest<AudienceRequest> requests) {
        return super.addValue(requests);
    }

    @PostMapping
    public ResponseEntity<Response> add(@Validated(InsertRequest.class) @RequestBody AudienceRequest request) {
        return super.addValue(request);
    }

    @GetMapping
    public ResponseEntity<Response> get(@Validated @RequestBody AudienceFindRequest request) {
        return super.get(request);
    }

    @GetMapping("/all/university-admin")
    public ResponseEntity<Page<Response>> getAllByUniversityAdmin(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        return super.getAllByUser(pageNumber, pageSize, service::getAllByUniversityAdmin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @PathVariable UUID id,
            @Validated(UpdateRequest.class) @RequestBody AudienceRequest request
    ) {
        return super.updateEntity(id, request);
    }
}
