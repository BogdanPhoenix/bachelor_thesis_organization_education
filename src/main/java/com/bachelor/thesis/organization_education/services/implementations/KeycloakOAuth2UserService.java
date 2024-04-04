package com.bachelor.thesis.organization_education.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeycloakOAuth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private final GrantedAuthoritiesMapper authoritiesMapper;
    private final JwtDecoder decoder;
    private final OidcUserService delegate = new OidcUserService();

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        Jwt accessToken = decoder.decode(userRequest.getAccessToken().getTokenValue());
        String clientId = userRequest.getClientRegistration().getClientId();

        Collection<? extends GrantedAuthority> authorities =
                extract(accessToken, clientId).stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

        authorities = authoritiesMapper.mapAuthorities(authorities);

        OidcUser user = delegate.loadUser(userRequest);
        Set<GrantedAuthority> authoritiesSet = new LinkedHashSet<>(user.getAuthorities());
        authoritiesSet.addAll(authorities);

        return new DefaultOidcUser(authoritiesSet, userRequest.getIdToken(), user.getUserInfo(), "preferred_username");
    }

    private List<String> extract(ClaimAccessor claims, String clientId) {
        Map<String, Object> attributes = claims.getClaims();

        return
                Optional.ofNullable(
                                (Map<String, Object>) attributes.get("resource_access"))
                        .map(resourceAccess ->
                                (Map<String, Map<String, Object>>) resourceAccess.get(clientId))
                        .map(clientResource ->
                                (List<String>) clientResource.get("roles"))
                        .orElse(Collections.emptyList());
    }
}
