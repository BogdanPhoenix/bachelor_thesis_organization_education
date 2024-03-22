package com.bachelor.thesis.organization_education.services.implementations.user;

import lombok.NonNull;
import jakarta.validation.Valid;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.bachelor.thesis.organization_education.dto.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import com.bachelor.thesis.organization_education.dto.user.Token;
import com.bachelor.thesis.organization_education.enums.TokenType;
import com.bachelor.thesis.organization_education.requests.user.UserRequest;
import com.bachelor.thesis.organization_education.responces.user.TokenResponse;
import com.bachelor.thesis.organization_education.services.interfaces.JwtService;
import com.bachelor.thesis.organization_education.responces.user.RegisteredResponse;
import com.bachelor.thesis.organization_education.repositories.user.TokenRepository;
import com.bachelor.thesis.organization_education.services.interfaces.user.TokenService;
import com.bachelor.thesis.organization_education.services.interfaces.user.UserCrudService;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.services.implementations.tools.ExceptionTools;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private static final String HEADER_START_FROM = "Bearer ";

    private final TokenRepository repository;
    private final JwtService jwtService;
    private final UserCrudService userService;

    @Override
    public RegisteredResponse createTokenForUser(@NonNull UserDetails user) throws NullPointerException {
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken);

        return RegisteredResponse
                .builder()
                .accessToken(jwtToken)
                .username(user.getUsername())
                .build();
    }

    @Override
    public TokenResponse refreshToken(@NonNull String token) throws JwtException, IllegalArgumentException, NotFindEntityInDataBaseException {
        var username = userNameFromToken(token);
        var request = UserRequest.builder().username(username).build();
        var user = userService.findAuthenticatedUser(request);
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(request);
        saveUserToken(user, accessToken);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String userNameFromToken(@NonNull String refreshToken) throws JwtException, IllegalArgumentException {
        var token = refreshToken.substring(HEADER_START_FROM.length());

        if(!jwtService.isTokenActive(token)) {
            ExceptionTools.throwRuntimeException("The token has expired", JwtException::new);
        }

        return jwtService.extractUsername(token);
    }

    private void revokeAllUserTokens(@NonNull @Valid UserRequest request) {
        var tokens = repository.findAllValidTokenByUser(request.getUsername());

        if(tokens.isEmpty()) {
            return;
        }

        tokens.forEach(this::revoke);
        repository.saveAll(tokens);
    }

    private void saveUserToken(@NonNull UserDetails userDetails, @NonNull String jwtToken) {
        var user = (User) userDetails;
        var token = Token
                .builder()
                .user(user)
                .accessToken(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        repository.save(token);
    }

    @Override
    public void revokeUserToken(@NonNull String accessToken) throws NotFindEntityInDataBaseException, JwtException {
        var storedToken = this.get(accessToken);
        this.revoke(storedToken);
        repository.save(storedToken);
    }

    private void revoke(@NonNull Token token) {
        token.setExpired(true);
        token.setRevoked(true);
    }

    @Override
    public Token get(@NonNull String accessToken) throws NotFindEntityInDataBaseException, JwtException {
        var token = repository
                .findByAccessToken(accessToken)
                .orElseThrow(() -> new NotFindEntityInDataBaseException("Unable to find the specified token"));

        if(!token.isEnabled()) {
            ExceptionTools.throwRuntimeException("The provided token is no longer active.", JwtException::new);
        }
        if(!jwtService.isTokenActive(accessToken)) {
            ExceptionTools.throwRuntimeException("Token already expired", JwtException::new);
        }

        return token;
    }
}
