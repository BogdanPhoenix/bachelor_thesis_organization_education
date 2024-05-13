package com.bachelor.thesis.organization_education.controllers.university;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.requests.general.ListRequest;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.controllers.abstracts.ResourceController;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.general.university.UniversityGroupRequest;
import com.bachelor.thesis.organization_education.requests.find.university.UniversityGroupFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityGroupService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/groups")
@Validated
public class UniversityGroupController extends ResourceController<UniversityGroupService> {
    @Autowired
    public UniversityGroupController(UniversityGroupService service) {
        super(service);
    }

    @PostMapping("/stream")
    public ResponseEntity<List<Response>> addStream(@Valid @RequestBody ListRequest<UniversityGroupRequest> requests) {
        var response = service.addValue(requests.collection());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<Response> add(@Validated(InsertRequest.class) @RequestBody UniversityGroupRequest request) {
        var response = service.addValue(request)
                .getResponse();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> get(@Validated @RequestBody UniversityGroupFindRequest request) {
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
            @Validated(UpdateRequest.class) @RequestBody UniversityGroupRequest request
    ) {
        return super.updateEntity(id, request);
    }
}
