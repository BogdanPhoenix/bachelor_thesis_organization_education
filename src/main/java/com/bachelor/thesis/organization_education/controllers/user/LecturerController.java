package com.bachelor.thesis.organization_education.controllers.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.controllers.ResourceController;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.general.user.LecturerRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.services.interfaces.user.LecturerService;

import java.util.UUID;
import java.security.Principal;

@RestController
@RequestMapping("/users/lecturer")
@Validated
public class LecturerController extends ResourceController<LecturerService> {
    @Autowired
    public LecturerController(LecturerService service) {
        super(service);
    }

    @PutMapping("/{lecturerId}/connect-with-discipline/{disciplineId}")
    public void connectWithDiscipline(
            @PathVariable UUID lecturerId,
            @PathVariable UUID disciplineId
    ) {
        service.addDiscipline(lecturerId, disciplineId);
    }

    @PutMapping("/{lecturerId}/disconnect-discipline/{disciplineId}")
    public void disconnectDiscipline(
            @PathVariable UUID lecturerId,
            @PathVariable UUID disciplineId
    ) {
        service.disconnectDiscipline(lecturerId, disciplineId);
    }

    @GetMapping
    public ResponseEntity<Response> get(Principal principal) {
        var uuid = UUID.fromString(principal.getName());
        return this.get(uuid);
    }

    @PutMapping
    public ResponseEntity<Response> update(
            Principal principal,
            @Validated(UpdateRequest.class) @RequestBody LecturerRequest request
    ) {
        var uuid = UUID.fromString(principal.getName());
        var response = service.updateValue(uuid, request)
                .getResponse();

        return ResponseEntity.ok(response);
    }
}
