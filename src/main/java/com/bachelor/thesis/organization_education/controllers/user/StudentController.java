package com.bachelor.thesis.organization_education.controllers.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.general.user.StudentRequest;
import com.bachelor.thesis.organization_education.services.interfaces.user.StudentService;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.controllers.abstracts.ResourceController;

import java.util.UUID;
import java.security.Principal;

@RestController
@RequestMapping("/users/student")
@Validated
public class StudentController extends ResourceController<StudentService> {
    @Autowired
    public StudentController(StudentService service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<Response> get(Principal principal) {
        var uuid = UUID.fromString(principal.getName());
        return this.get(uuid);
    }

    @PutMapping
    public ResponseEntity<Response> update(
            Principal principal,
            @Validated(UpdateRequest.class) @RequestBody StudentRequest request
    ) {
        var uuid = UUID.fromString(principal.getName());
        var response = service.updateValue(uuid, request)
                .getResponse();

        return ResponseEntity.ok(response);
    }
}
