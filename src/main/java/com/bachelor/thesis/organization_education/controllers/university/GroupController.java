package com.bachelor.thesis.organization_education.controllers.university;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.controllers.ResourceController;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.university.GroupRequest;
import com.bachelor.thesis.organization_education.requests.find.university.GroupFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.GroupService;

@RestController
@RequestMapping("/groups")
@Validated
public class GroupController extends ResourceController<GroupService> {
    @Autowired
    public GroupController(GroupService service) {
        super(service);
    }

    @PostMapping
    public ResponseEntity<Response> add(@Validated(InsertRequest.class) @RequestBody GroupRequest request) {
        var response = service.addValue(request)
                .getResponse();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> get(@Validated @RequestBody GroupFindRequest request) {
        return super.get(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @PathVariable Long id,
            @Validated(UpdateRequest.class) @RequestBody GroupRequest request
    ) {
        return super.updateEntity(id, request);
    }
}
