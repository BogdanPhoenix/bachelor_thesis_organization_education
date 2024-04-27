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
import com.bachelor.thesis.organization_education.requests.general.university.AcademicDisciplineRequest;
import com.bachelor.thesis.organization_education.requests.find.university.AcademicDisciplineFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.AcademicDisciplineService;

@RestController
@RequestMapping("/disciplines")
@Validated
public class AcademicDisciplineController extends ResourceController<AcademicDisciplineService> {
    @Autowired
    public AcademicDisciplineController(AcademicDisciplineService service) {
        super(service);
    }

    @PostMapping
    public ResponseEntity<Response> add(@Validated(InsertRequest.class) @RequestBody AcademicDisciplineRequest request) {
        var response = service.addValue(request)
                .getResponse();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{disciplineId}/connect-with-lecturer/{lecturerId}")
    public void connectWithLecturer(
            @PathVariable Long disciplineId,
            @PathVariable Long lecturerId
    ) {
        service.addLecturer(disciplineId, lecturerId);
    }

    @GetMapping
    public ResponseEntity<Response> get(@Validated @RequestBody AcademicDisciplineFindRequest request) {
        return super.get(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @PathVariable Long id,
            @Validated(UpdateRequest.class) @RequestBody AcademicDisciplineRequest request
    ) {
        return super.updateEntity(id, request);
    }
}
