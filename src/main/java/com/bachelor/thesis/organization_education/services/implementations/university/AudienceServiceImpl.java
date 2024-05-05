package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.Audience;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.repositories.university.AudienceRepository;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.requests.general.university.AudienceRequest;
import com.bachelor.thesis.organization_education.requests.find.university.AudienceFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.AudienceService;
import com.bachelor.thesis.organization_education.services.interfaces.university.ScheduleService;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityService;
import com.bachelor.thesis.organization_education.services.implementations.crud.CrudServiceAbstract;

import java.util.List;
import java.util.UUID;

@Service
public class AudienceServiceImpl extends CrudServiceAbstract<Audience, AudienceRepository> implements AudienceService {
    @Autowired
    public AudienceServiceImpl(AudienceRepository repository, ApplicationContext context) {
        super(repository, "Audiences", context);
    }

    @Override
    protected Audience createEntity(InsertRequest request) {
        var insertRequest = (AudienceRequest) request;
        return Audience.builder()
                .university(insertRequest.getUniversity())
                .numFloor(insertRequest.getNumFloor())
                .numAudience(insertRequest.getNumAudience())
                .numSeats(insertRequest.getNumSeats())
                .build();
    }

    @Override
    public BaseTableInfo addResource(@NonNull AudienceRequest request, @NonNull String adminId) throws NullPointerException, DuplicateException {
        var university = getUniversity(adminId);
        request.setUniversity(university);
        return super.addValue(request);
    }

    @Override
    public Audience updateValue(@NonNull UUID id, @NonNull UpdateRequest request) throws DuplicateException, NotFindEntityInDataBaseException {
        var entity = findValueById(id);
        return updateValue(entity, request);
    }

    private University getUniversity(String adminId) {
        var uuid = UUID.fromString(adminId);
        var universityService = getBeanByClass(UniversityService.class);
        return universityService.findByUser(uuid);
    }

    @Override
    protected List<Audience> findAllEntitiesByRequest(@NonNull FindRequest request) {
        var findRequest = (AudienceFindRequest) request;

        return repository.findAllByUniversityAndNumFloorAndNumAudience(
                findRequest.getUniversity(),
                findRequest.getNumFloor(),
                findRequest.getNumAudience()
        );
    }

    @Override
    protected void updateEntity(Audience entity, UpdateRequest request) {
        var updateRequest = (AudienceRequest) request;

        if(!updateRequest.numSeatsIsEmpty()) {
            entity.setNumSeats(updateRequest.getNumSeats());
        }
    }

    @Override
    protected void selectedForDeactivateChild(UUID id) {
        var entity = repository.findById(id);
        entity.ifPresent(e -> deactivatedChild(e.getSchedules(), ScheduleService.class));
    }
}
