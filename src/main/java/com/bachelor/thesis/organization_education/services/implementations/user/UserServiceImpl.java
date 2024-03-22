package com.bachelor.thesis.organization_education.services.implementations.user;

import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.requests.user.RegistrationRequest;
import com.bachelor.thesis.organization_education.requests.user.UserRequest;
import com.bachelor.thesis.organization_education.responces.user.TokenResponse;
import com.bachelor.thesis.organization_education.responces.user.RegisteredResponse;
import com.bachelor.thesis.organization_education.services.interfaces.user.TokenService;
import com.bachelor.thesis.organization_education.services.interfaces.user.UserCrudService;
import com.bachelor.thesis.organization_education.services.interfaces.user.UserService;
import io.jsonwebtoken.JwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final TokenService tokenService;
    private final UserCrudService crudService;

    @Override
    public RegisteredResponse registration(@NonNull RegistrationRequest request) throws DuplicateException, NullPointerException {
        var userDetails = crudService.createUser(request);
        return tokenService.createTokenForUser(userDetails);
    }

    @Override
    public TokenResponse refreshToken(@NonNull String token) throws JwtException, IllegalArgumentException, NotFindEntityInDataBaseException {
        return tokenService.refreshToken(token);
    }

    @Override
    public void verifyEmail(@NonNull String token) throws NotFindEntityInDataBaseException, JwtException {
        var theToken = tokenService.get(token);
        var user = theToken.getUser();
        var request = UserRequest
                .builder()
                .username(user.getUsername())
                .build();

        crudService.enable(request);
    }
}
