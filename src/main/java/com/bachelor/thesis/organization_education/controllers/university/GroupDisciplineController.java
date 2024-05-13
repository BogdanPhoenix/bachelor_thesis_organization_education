package com.bachelor.thesis.organization_education.controllers.university;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.requests.general.ListRequest;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.responces.university.MagazineResponse;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.controllers.abstracts.ResourceController;
import com.bachelor.thesis.organization_education.requests.general.university.GroupDisciplineRequest;
import com.bachelor.thesis.organization_education.requests.find.university.GroupDisciplineFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.GroupDisciplineService;

import java.util.List;
import java.util.UUID;
import java.security.Principal;

@RestController
@RequestMapping("/groups-disciplines")
@Validated
public class GroupDisciplineController extends ResourceController<GroupDisciplineService> {
    @Autowired
    public GroupDisciplineController(GroupDisciplineService service) {
        super(service);
    }

    @PostMapping("/stream")
    public ResponseEntity<List<Response>> addStream(@Valid @RequestBody ListRequest<GroupDisciplineRequest> requests) {
        return super.addValue(requests);
    }

    @PostMapping
    public ResponseEntity<Response> add(@Validated(InsertRequest.class) @RequestBody GroupDisciplineRequest request) {
        return super.addValue(request);
    }

    @GetMapping
    public ResponseEntity<Response> get(@Validated @RequestBody GroupDisciplineFindRequest request) {
        return super.get(request);
    }

    @GetMapping("/all/university-admin")
    public ResponseEntity<Page<Response>> getAllByLecturer(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        return super.getAllByUser(pageNumber, pageSize, service::getAllByUniversityAdmin);
    }

    @GetMapping({"/for-lecturer/magazines/{id}", "/magazines/{id}"})
    public ResponseEntity<MagazineResponse> getMagazine(@PathVariable UUID id) {
        var response = service.getMagazine(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/for-lecturer/magazines")
    public ResponseEntity<Page<MagazineResponse>> getMagazines(
            Principal principal,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        var uuid = UUID.fromString(principal.getName());
        var pageable = PageRequest.of(pageNumber, pageSize);
        var response = service.getMagazinesByLecturer(uuid, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/magazines")
    public ResponseEntity<Page<MagazineResponse>> getMagazines(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var response = service.getAllMagazine(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/for-student/magazines/{id}")
    public ResponseEntity<MagazineResponse> getMagazine(
            Principal principal,
            @PathVariable UUID id
    ) {
        var uuid = UUID.fromString(principal.getName());
        var response = service.getMagazine(uuid, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @PathVariable UUID id,
            @Validated(UpdateRequest.class) @RequestBody GroupDisciplineRequest request
    ) {
        return super.updateEntity(id, request);
    }
}
