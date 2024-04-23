package com.bachelor.thesis.organization_education.controllers.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import com.bachelor.thesis.organization_education.controllers.ResourceController;
import com.bachelor.thesis.organization_education.responces.user.LecturerResponse;
import com.bachelor.thesis.organization_education.requests.find.user.LecturerFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.user.LecturerService;
import com.bachelor.thesis.organization_education.requests.update.user.LecturerUpdateRequest;

import java.util.UUID;
import java.security.Principal;

@RestController
@RequestMapping("/users/lecture")
public class LecturerController extends ResourceController<LecturerService> {
    @Autowired
    public LecturerController(LecturerService service) {
        super(service);
    }

    @PreAuthorize("hasRole('LECTURER')")
    @GetMapping
    public ResponseEntity<LecturerResponse> get(Principal principal) {
        var uuid = UUID.fromString(principal.getName());
        return this.get(uuid);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<LecturerResponse> get(@PathVariable UUID userId) {
        var request = new LecturerFindRequest(userId);
        return super.get(request);
    }

    @PreAuthorize("hasRole('LECTURER')")
    @PutMapping
    public ResponseEntity<LecturerResponse> update(
            Principal principal,
            @RequestBody @Valid LecturerUpdateRequest request
    ) {
        var uuid = UUID.fromString(principal.getName());
        var response = service.updateValue(uuid, request)
                .getResponse();

        return ResponseEntity.ok(response);
    }
}
