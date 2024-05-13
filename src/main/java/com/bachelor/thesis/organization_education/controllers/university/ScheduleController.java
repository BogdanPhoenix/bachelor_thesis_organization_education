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
import com.bachelor.thesis.organization_education.controllers.abstracts.ResourceController;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.general.university.ScheduleRequest;
import com.bachelor.thesis.organization_education.requests.find.university.ScheduleFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.ScheduleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/schedules")
@Validated
public class ScheduleController extends ResourceController<ScheduleService> {
    @Autowired
    public ScheduleController(ScheduleService service) {
        super(service);
    }

    @PostMapping("/stream")
    public ResponseEntity<List<Response>> addStream(@Valid @RequestBody ListRequest<ScheduleRequest> requests) {
        return super.addValue(requests);
    }

    @PostMapping
    public ResponseEntity<Response> add(@Validated(InsertRequest.class) @RequestBody ScheduleRequest request) {
        return super.addValue(request);
    }

    @GetMapping
    public ResponseEntity<Response> get(@Validated @RequestBody ScheduleFindRequest request) {
        return super.get(request);
    }

    @GetMapping("/all/university-admin")
    public ResponseEntity<Page<Response>> getAllByUniversityAdmin(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        return super.getAllByUser(pageNumber, pageSize, service::getAllByUniversityAdmin);
    }

    @GetMapping("/all/lecturer")
    public ResponseEntity<Page<Response>> getAllByLecturer(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        return super.getAllByUser(pageNumber, pageSize, service::getAllByLecturer);
    }

    @GetMapping("/all/student")
    public ResponseEntity<Page<Response>> getAllByStudent(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        return super.getAllByUser(pageNumber, pageSize, service::getAllByStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @PathVariable UUID id,
            @Validated(UpdateRequest.class) @RequestBody ScheduleRequest request
    ) {
        return super.updateEntity(id, request);
    }
}
