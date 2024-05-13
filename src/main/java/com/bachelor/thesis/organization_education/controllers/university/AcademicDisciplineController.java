package com.bachelor.thesis.organization_education.controllers.university;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.requests.general.ListRequest;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.controllers.abstracts.ResourceController;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.general.university.AcademicDisciplineRequest;
import com.bachelor.thesis.organization_education.requests.find.university.AcademicDisciplineFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.AcademicDisciplineService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/disciplines")
@Validated
public class AcademicDisciplineController extends ResourceController<AcademicDisciplineService> {
    @Autowired
    public AcademicDisciplineController(AcademicDisciplineService service) {
        super(service);
    }

    @PostMapping("/stream")
    public ResponseEntity<List<Response>> addStream(@Valid @RequestBody ListRequest<AcademicDisciplineRequest> requests) {
        return super.addValue(requests);
    }

    @PostMapping
    public ResponseEntity<Response> add(@Validated(InsertRequest.class) @RequestBody AcademicDisciplineRequest request) {
        return super.addValue(request);
    }

    @PutMapping("/{disciplineId}/connect-with-lecturer/{lecturerId}")
    public void connectWithLecturer(
            @PathVariable UUID disciplineId,
            @PathVariable UUID lecturerId
    ) {
        service.addLecturer(disciplineId, lecturerId);
    }

    @PutMapping("/{disciplineId}/disconnect-lecturer/{lecturerId}")
    public void disconnectLecturer(
            @PathVariable UUID disciplineId,
            @PathVariable UUID lecturerId
    ) {
        service.disconnectLecturer(disciplineId, lecturerId);
    }

    @GetMapping
    public ResponseEntity<Response> get(@Validated @RequestBody AcademicDisciplineFindRequest request) {
        return super.get(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @PathVariable UUID id,
            @Validated(UpdateRequest.class) @RequestBody AcademicDisciplineRequest request
    ) {
        return super.updateEntity(id, request);
    }
}
