package com.bachelor.thesis.organization_education.services.implementations.user;

import com.bachelor.thesis.organization_education.dto.user.User;
import com.bachelor.thesis.organization_education.dto.user.UserInfo;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.repositories.user.UserInfoRepository;
import com.bachelor.thesis.organization_education.requests.abstract_type.Request;
import com.bachelor.thesis.organization_education.requests.user.UserInfoRequest;
import com.bachelor.thesis.organization_education.services.implementations.CrudServiceAbstract;
import com.bachelor.thesis.organization_education.services.interfaces.user.UserInfoService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoServiceImpl extends CrudServiceAbstract<UserInfo, UserInfoRepository> implements UserInfoService {
    @Autowired
    protected UserInfoServiceImpl(UserInfoRepository repository) {
        super(repository, "Users info");
    }

    @Override
    protected UserInfo createEntity(@NonNull Request request) {
        var infoRequest = (UserInfoRequest) request;
        var userResponse = infoRequest.getUser();
        var user = User
                .builder()
                .id(userResponse.getId())
                .username(userResponse.getUsername())
                .password("")
                .role(userResponse.getRole())
                .build();

        return UserInfo
                .builder()
                .user(user)
                .firstName(infoRequest.getFirstName())
                .lastName(infoRequest.getLastName())
                .build();
    }

    @Override
    protected UserInfo getEntity(@NonNull Request request) throws NotFindEntityInDataBaseException {
        return findEntity(request)
                .orElseThrow(() -> new NotFindEntityInDataBaseException("Unable to find user information."));
    }

    @Override
    protected Optional<UserInfo> findEntity(@NonNull Request request) {
        var infoRequest = (UserInfoRequest) request;
        return repository.findByFirstNameAndLastName(
                infoRequest.getFirstName(),
                infoRequest.getLastName()
        );
    }
}
