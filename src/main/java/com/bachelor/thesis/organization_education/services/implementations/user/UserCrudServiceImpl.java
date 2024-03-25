package com.bachelor.thesis.organization_education.services.implementations.user;

import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.dto.user.User;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.repositories.user.UserRepository;
import com.bachelor.thesis.organization_education.requests.abstract_type.Request;
import com.bachelor.thesis.organization_education.requests.user.RegistrationRequest;
import com.bachelor.thesis.organization_education.requests.user.UserInfoRequest;
import com.bachelor.thesis.organization_education.requests.user.UserRequest;
import com.bachelor.thesis.organization_education.responces.user.UserResponse;
import com.bachelor.thesis.organization_education.services.implementations.CrudServiceAbstract;
import com.bachelor.thesis.organization_education.services.interfaces.user.UserCrudService;
import com.bachelor.thesis.organization_education.services.interfaces.user.UserInfoService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

@Service
public class UserCrudServiceImpl extends CrudServiceAbstract<User, UserRepository> implements UserCrudService {
    private final PasswordEncoder passwordEncoder;
    private final UserInfoService userInfoService;

    @Autowired
    protected UserCrudServiceImpl(
            UserRepository repository,
            UserInfoService userInfoService,
            PasswordEncoder passwordEncoder
    ) {
        super(repository, "Registered users");
        this.userInfoService = userInfoService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails createUser(@NonNull RegistrationRequest request) throws DuplicateException, NullPointerException {
        var response = (UserResponse) super.addValue(request);
        var user = createEntity(request);
        user.setId(response.getId());

        var infoRequest = UserInfoRequest
                .builder()
                .user(response)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        userInfoService.addValue(infoRequest);

        return user;
    }

    @Override
    protected User createEntity(@NonNull Request request) {
        var userRequest = (RegistrationRequest) request;

        return User.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(userRequest.getRole())
                .build();
    }

    @Override
    public User findAuthenticatedUser(@NonNull UserRequest request) throws NotFindEntityInDataBaseException {
        return findEntity(request)
                .filter(BaseTableInfo::isEnabled)
                .orElseThrow(() -> new NotFindEntityInDataBaseException("User could not be found."));
    }

    @Override
    public void enable(@NonNull Request request) throws NotFindEntityInDataBaseException {
        super.enable(request);
        actionOnUserInfo(request, userInfoService::enable);
    }

    @Override
    public void disable(@NonNull Request request) throws NotFindEntityInDataBaseException {
        super.disable(request);
        actionOnUserInfo(request, userInfoService::disable);
    }

    private void actionOnUserInfo(Request request, @NonNull Consumer<Request> activationMethod) {
        var user = getEntity(request);
        var userInfo = user.getInfoUser();
        var infoRequest = UserInfoRequest.builder()
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .build();

        activationMethod.accept(infoRequest);
    }

    @Override
    protected User getEntity(@NonNull Request request) throws NotFindEntityInDataBaseException {
        return findEntity(request)
                .orElseThrow(() -> new NotFindEntityInDataBaseException("User could not be found"));
    }

    @Override
    protected Optional<User> findEntity(@NonNull Request request) {
        UserRequest userRequest;

        if(request instanceof RegistrationRequest registrationRequest) {
            userRequest = UserRequest.builder()
                    .username(registrationRequest.getUsername())
                    .build();
        }
        else {
            userRequest = (UserRequest) request;
        }

        return repository.findByUsername(
                userRequest.getUsername()
        );
    }
}
