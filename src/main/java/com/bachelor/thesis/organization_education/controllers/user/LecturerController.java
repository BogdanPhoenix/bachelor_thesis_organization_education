package com.bachelor.thesis.organization_education.controllers.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.general.user.LecturerRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.controllers.abstracts.ResourceController;
import com.bachelor.thesis.organization_education.services.interfaces.user.LecturerService;

import java.util.Set;
import java.util.UUID;
import java.security.Principal;
import java.util.stream.Collectors;

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

    @GetMapping("/disciplines")
    public ResponseEntity<Set<Response>> getDisciplines(Principal principal) {
        var uuid = UUID.fromString(principal.getName());
        return getDisciplines(uuid);
    }

    @GetMapping("/disciplines/{lecturerId}")
    public ResponseEntity<Set<Response>> getDisciplines(@PathVariable UUID lecturerId) {
        var responses = service.getDisciplines(lecturerId)
                .stream()
                .map(BaseTableInfo::getResponse)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(responses);
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
