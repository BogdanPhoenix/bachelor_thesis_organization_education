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
import com.bachelor.thesis.organization_education.requests.general.university.GroupDisciplineRequest;
import com.bachelor.thesis.organization_education.requests.find.university.GroupDisciplineFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.GroupDisciplineService;

import java.util.UUID;

@RestController
@RequestMapping("/groups-disciplines")
@Validated
public class GroupDisciplineController extends ResourceController<GroupDisciplineService> {
    @Autowired
    public GroupDisciplineController(GroupDisciplineService service) {
        super(service);
    }

    @PostMapping
    public ResponseEntity<Response> add(@Validated(InsertRequest.class) @RequestBody GroupDisciplineRequest request) {
        var response = service.addValue(request)
                .getResponse();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> get(@Validated @RequestBody GroupDisciplineFindRequest request) {
        return super.get(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @PathVariable UUID id,
            @Validated(UpdateRequest.class) @RequestBody GroupDisciplineRequest request
    ) {
        return super.updateEntity(id, request);
    }
}
