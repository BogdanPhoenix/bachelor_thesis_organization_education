package com.bachelor.thesis.organization_education.controllers.university;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.data.domain.Page;
import org.springframework.util.MimeTypeUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import com.bachelor.thesis.organization_education.requests.general.ListRequest;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.controllers.abstracts.ResourceController;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.StorageService;
import com.bachelor.thesis.organization_education.requests.general.university.ClassRecordingRequest;
import com.bachelor.thesis.organization_education.requests.find.university.ClassRecordingFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.ClassRecordingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/class-recordings")
@Validated
public class ClassRecordingController extends ResourceController<ClassRecordingService> {
    private final StorageService storageService;

    @Autowired
    public ClassRecordingController(ClassRecordingService service, StorageService storageService) {
        super(service);
        this.storageService = storageService;
    }

    @PostMapping("/stream")
    public ResponseEntity<List<Response>> addStream(@Valid @RequestBody ListRequest<ClassRecordingRequest> requests) {
        return super.addValue(requests);
    }

    @PostMapping
    public ResponseEntity<Response> add(@Validated(InsertRequest.class) @RequestBody ClassRecordingRequest request) {
        return super.addValue(request);
    }

    @GetMapping
    public ResponseEntity<Response> get(@Validated @RequestBody ClassRecordingFindRequest request) {
        return super.get(request);
    }

    @GetMapping("/all/lecturer")
    public ResponseEntity<Page<Response>> getAllByLecturer(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        return super.getAllByUser(pageNumber, pageSize, service::getAllByLecturer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @PathVariable UUID id,
            @Validated(UpdateRequest.class) @RequestBody ClassRecordingRequest request
    ) {
        return super.updateEntity(id, request);
    }

    @PreAuthorize("hasRole('LECTURER') || hasRole('STUDENT')")
    @PostMapping("/{id}/files/upload")
    public ResponseEntity<Response> uploadFile(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file
    ) {
        var response = storageService.uploadStorage(id, file)
                .getResponse();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('LECTURER') || hasRole('STUDENT')")
    @GetMapping("/{id}/files")
    public ResponseEntity<Page<Response>> getFiles(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var response = storageService.getStorages(id, pageable);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('LECTURER') || hasRole('STUDENT')")
    @GetMapping("/files/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable UUID id) {
        var response = storageService.downloadStorage(id);
        var mediaType = MediaType.valueOf(MimeTypeUtils.parseMimeType(response.getFileType()).toString());

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(response.getFileData());
    }

    @PreAuthorize("hasRole('LECTURER') || hasRole('STUDENT')")
    @DeleteMapping("/files/{id}")
    public ResponseEntity<Void> deactivateFile(@PathVariable UUID id) {
        storageService.deactivate(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/files/delete/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable UUID id) {
        storageService.deleteValue(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/files/activate/{id}")
    public ResponseEntity<Void> activateFile(@PathVariable UUID id) {
        storageService.activate(id);
        return ResponseEntity.ok().build();
    }
}
