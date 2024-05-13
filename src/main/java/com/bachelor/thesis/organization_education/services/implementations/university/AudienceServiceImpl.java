package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.Audience;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.repositories.university.AudienceRepository;
import com.bachelor.thesis.organization_education.requests.general.university.AudienceRequest;
import com.bachelor.thesis.organization_education.requests.find.university.AudienceFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.AudienceService;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityService;
import com.bachelor.thesis.organization_education.services.implementations.crud.CrudServiceAbstract;

import java.util.UUID;
import java.util.List;
import java.util.Collection;

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
    public List<Response> addValue(@NonNull Collection<? extends InsertRequest> requests) throws NullPointerException, DuplicateException {
        var university = getUniversity();

        for(var request : requests) {
            var insertRequest = (AudienceRequest) request;
            insertRequest.setUniversity(university);
        }

        return super.addValue(requests);
    }

    @Override
    public Audience addValue(@NonNull InsertRequest request) throws DuplicateException, NullPointerException {
        var insertRequest = (AudienceRequest) request;
        var university = getUniversity();
        insertRequest.setUniversity(university);
        return super.addValue(insertRequest);
    }

    @Override
    public Page<Response> getAllByUniversityAdmin(@NonNull Pageable pageable) {
        var uuid = super.getAuthenticationUUID();
        return repository.findAll(uuid, pageable)
                .map(BaseTableInfo::getResponse);
    }

    private University getUniversity() {
        var uuid = super.getAuthenticationUUID();
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
        updateIfPresent(updateRequest::getNumSeats, entity::setNumSeats);
    }

    @Override
    protected boolean checkOwner(Audience entity, UUID userId) {
        return entity.getUniversity()
                .getAdminId()
                .equals(userId);
    }

    @Override
    protected void selectedForDeactivateChild(Audience entity) {
        deactivatedChild(entity.getSchedules(), ScheduleServiceImpl.class);
    }
}
