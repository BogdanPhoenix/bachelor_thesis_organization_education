package com.bachelor.thesis.organization_education.services.implementations.user;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.Lecturer;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.repositories.user.LecturerRepository;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.general.user.LecturerRequest;
import com.bachelor.thesis.organization_education.requests.find.user.LecturerFindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.services.interfaces.user.LecturerService;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.requests.insert.abstracts.RegistrationRequest;
import com.bachelor.thesis.organization_education.requests.insert.user.RegistrationLecturerRequest;
import com.bachelor.thesis.organization_education.services.implementations.crud.CrudServiceAbstract;

import java.util.UUID;
import java.util.Optional;

@Service
public class LecturerServiceImpl extends CrudServiceAbstract<Lecturer, LecturerRepository> implements LecturerService {
    @Autowired
    public LecturerServiceImpl(LecturerRepository repository) {
        super(repository, "Lectures");
    }

    @Override
    protected void objectFormation(InsertRequest request) {

    }

    @Override
    protected Lecturer createEntity(InsertRequest request) {
        var lectureRequest = (LecturerRequest) request;
        return Lecturer.builder()
                .title(lectureRequest.getTitle())
                .degree(lectureRequest.getDegree())
                .faculty(lectureRequest.getFaculty())
                .user(lectureRequest.getUserId())
                .build();
    }

    @Override
    public void registration(@NonNull RegistrationRequest request, @NonNull String userId) {
        var lectureRegistrationRequest = (RegistrationLecturerRequest) request;
        var lectureRequest = LecturerRequest.builder()
                .title(lectureRegistrationRequest.getTitle())
                .degree(lectureRegistrationRequest.getDegree())
                .faculty(lectureRegistrationRequest.getFaculty())
                .userId(UUID.fromString(userId))
                .build();

        super.addValue(lectureRequest);
    }

    @Override
    protected Optional<Lecturer> findEntityByRequest(@NonNull FindRequest request) {
        var findRequest = (LecturerFindRequest) request;
        return repository.findByUser(findRequest.getUserId());
    }

    @Override
    protected void updateEntity(Lecturer entity, UpdateRequest request) {
        var lectureRequest = (LecturerRequest) request;

        if(!lectureRequest.titleIsEmpty()) {
            entity.setTitle(lectureRequest.getTitle());
        }
        if (!lectureRequest.degreeIsEmpty()) {
            entity.setDegree(lectureRequest.getDegree());
        }
        if(!lectureRequest.facultyIsEmpty()) {
            entity.setFaculty(lectureRequest.getFaculty());
        }
    }

    @Override
    public Lecturer updateValue(@NonNull UUID id, @NonNull UpdateRequest request) throws NotFindEntityInDataBaseException {
        var findRequest = new LecturerFindRequest(id);
        var entity = getValue(findRequest);

        return updateValue(entity, request);
    }

    @Override
    public void activate(@NonNull String userId) {
        updateEnabled(userId, true);
    }

    @Override
    public void deactivate(@NonNull String userId) {
        updateEnabled(userId, false);
    }

    private void updateEnabled(String adminId, boolean value) throws NotFindEntityInDataBaseException {
        var entity = getEntity(adminId);
        entity.ifPresent(e -> {
            selectedForDeactivateChild(e.getId());
            super.updateEnabled(e, value);
        });
    }

    @Override
    public void deleteValue(@NonNull String userId) {
        var entity = getEntity(userId);
        entity.ifPresent(repository::delete);
    }

    private Optional<Lecturer> getEntity(String adminId) {
        var uuid = UUID.fromString(adminId);
        var request = new LecturerFindRequest(uuid);
        return findEntityByRequest(request);
    }

    @Override
    protected void selectedForDeactivateChild(Long id) { }
}
