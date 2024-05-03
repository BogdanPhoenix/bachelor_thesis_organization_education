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
import com.bachelor.thesis.organization_education.requests.general.university.ScheduleRequest;
import com.bachelor.thesis.organization_education.requests.find.university.ScheduleFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.ScheduleService;

import java.util.UUID;

@RestController
@RequestMapping("/schedules")
@Validated
public class ScheduleController extends ResourceController<ScheduleService> {
    @Autowired
    public ScheduleController(ScheduleService service) {
        super(service);
    }

    @PostMapping
    public ResponseEntity<Response> add(@Validated(InsertRequest.class) @RequestBody ScheduleRequest request) {
        var response = service.addValue(request)
                .getResponse();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> get(@Validated @RequestBody ScheduleFindRequest request) {
        return super.get(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @PathVariable UUID id,
            @Validated(UpdateRequest.class) @RequestBody ScheduleRequest request
    ) {
        return super.updateEntity(id, request);
    }
}
