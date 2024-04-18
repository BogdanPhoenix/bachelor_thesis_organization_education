package com.bachelor.thesis.organization_education.services.implementations.user;

import com.bachelor.thesis.organization_education.dto.Lecture;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.repositories.user.LectureRepository;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.find.user.LectureFindRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.Request;
import com.bachelor.thesis.organization_education.requests.general.user.LectureRequest;
import com.bachelor.thesis.organization_education.requests.insert.abstracts.RegistrationRequest;
import com.bachelor.thesis.organization_education.requests.insert.user.RegistrationLectureRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.update.user.LectureUpdateRequest;
import com.bachelor.thesis.organization_education.services.implementations.crud.CrudServiceAbstract;
import com.bachelor.thesis.organization_education.services.interfaces.user.LectureService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class LectureServiceImpl extends CrudServiceAbstract<Lecture, LectureRepository> implements LectureService {
    @Autowired
    public LectureServiceImpl(LectureRepository repository) {
        super(repository, "Lectures");
    }

    @Override
    protected Lecture createEntity(@NonNull Request request) {
        var lectureRequest = (LectureRequest) request;
        return Lecture.builder()
                .title(lectureRequest.getTitle())
                .degree(lectureRequest.getDegree())
                .faculty(lectureRequest.getFaculty())
                .user(lectureRequest.getUserId())
                .build();
    }

    @Override
    public void registration(@NonNull RegistrationRequest request, @NonNull String userId) {
        var lectureRegistrationRequest = (RegistrationLectureRequest) request;
        var lectureRequest = LectureRequest.builder()
                .title(lectureRegistrationRequest.getTitle())
                .degree(lectureRegistrationRequest.getDegree())
                .faculty(lectureRegistrationRequest.getFaculty())
                .userId(UUID.fromString(userId))
                .build();

        super.addValue(lectureRequest);
    }

    @Override
    protected Optional<Lecture> findEntity(@NonNull FindRequest request) {
        var findRequest = (LectureFindRequest) request;
        return repository.findByUser(findRequest.getUserId());
    }

    @Override
    protected void updateEntity(Lecture entity, UpdateRequest request) {
        var lectureRequest = (LectureUpdateRequest) request;

        if(lectureRequest.getTitle() != null) {
            entity.setTitle(lectureRequest.getTitle());
        }
        if (lectureRequest.getDegree() != null) {
            entity.setDegree(lectureRequest.getDegree());
        }
        if(lectureRequest.getFaculty() != null) {
            entity.setFaculty(lectureRequest.getFaculty());
        }
    }

    @Override
    public Lecture updateValue(@NonNull UUID id, @NonNull UpdateRequest request) throws NotFindEntityInDataBaseException {
        var findRequest = new LectureFindRequest(id);
        var entity = getValue(findRequest);

        updateEntity(entity, request);
        entity.setUpdateDate(LocalDateTime.now());
        return repository.save(entity);
    }

    @Override
    protected void selectedForDeactivateChild(FindRequest request) {

    }
}
