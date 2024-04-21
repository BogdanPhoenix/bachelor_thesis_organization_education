package com.bachelor.thesis.organization_education.controllers.user;

import com.bachelor.thesis.organization_education.controllers.university.ResourceController;
import com.bachelor.thesis.organization_education.requests.find.user.LectureFindRequest;
import com.bachelor.thesis.organization_education.requests.update.user.LectureUpdateRequest;
import com.bachelor.thesis.organization_education.responces.user.LectureResponse;
import com.bachelor.thesis.organization_education.services.interfaces.user.LecturerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/users/lecture")
public class LecturerController extends ResourceController<LecturerService> {
    @Autowired
    public LecturerController(LecturerService service) {
        super(service);
    }

    @PreAuthorize("hasRole('LECTURER')")
    @GetMapping
    public ResponseEntity<LectureResponse> get(Principal principal) {
        var uuid = UUID.fromString(principal.getName());
        return get(uuid);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<LectureResponse> get(@PathVariable UUID userId) {
        var request = new LectureFindRequest(userId);
        var response = (LectureResponse) service.getValue(request)
                .getResponse();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('LECTURER')")
    @PutMapping
    public ResponseEntity<LectureResponse> update(
            @RequestBody @Valid LectureUpdateRequest request,
            BindingResult bindingResult,
            Principal principal
    ) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        var uuid = UUID.fromString(principal.getName());
        var response = service.updateValue(uuid, request)
                .getResponse();
        return ResponseEntity.ok(response);
    }
}
