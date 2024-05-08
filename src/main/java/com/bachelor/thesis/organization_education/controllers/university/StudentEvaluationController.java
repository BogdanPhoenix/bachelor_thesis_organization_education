package com.bachelor.thesis.organization_education.controllers.university;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.requests.general.ListRequest;
import com.bachelor.thesis.organization_education.controllers.ResourceController;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.general.university.StudentEvaluationRequest;
import com.bachelor.thesis.organization_education.requests.find.university.StudentEvaluationFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.StudentEvaluationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students-evaluations")
@Validated
public class StudentEvaluationController extends ResourceController<StudentEvaluationService> {
    @Autowired
    public StudentEvaluationController(StudentEvaluationService service) {
        super(service);
    }

    @PostMapping("/stream")
    public ResponseEntity<List<Response>> addStream(@Valid @RequestBody ListRequest<StudentEvaluationRequest> requests) {
        var response = service.addValue(requests.collection());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<Response> add(@Validated(InsertRequest.class) @RequestBody StudentEvaluationRequest request) {
        var response = service.addValue(request)
                .getResponse();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> get(@Validated @RequestBody StudentEvaluationFindRequest request) {
        return super.get(request);
    }

    @GetMapping("/recording/{id}")
    public Page<Response> getEvaluationsForRecording(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "30") int pageSize
    ) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        return service.getEvaluationsForRecording(id, pageable)
                .map(BaseTableInfo::getResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @PathVariable UUID id,
            @Validated(UpdateRequest.class) @RequestBody StudentEvaluationRequest request
    ) {
        return super.updateEntity(id, request);
    }
}
