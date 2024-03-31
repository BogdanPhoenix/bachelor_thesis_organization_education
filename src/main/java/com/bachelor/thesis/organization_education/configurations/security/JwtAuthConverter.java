package com.bachelor.thesis.organization_education.configurations.security;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private static final String REALM_NAME = "roles";

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Value("${jwt.auth.converter.resource-id}")
    private String resourceId;


    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        var jwtConvert = jwtGrantedAuthoritiesConverter.convert(jwt);

        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtConvert.stream(),
                extractResourceRoles(jwt).stream()).collect(Collectors.toSet());
        return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));
    }

    private String getPrincipalClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        return jwt.getClaim(claimName);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
        Map<String, Map<String, Collection<String>>> resourceAccess = jwt.getClaim("resource_access");

        Collection<String> allRoles = new ArrayList<>();

        if(resourceAccess != null && resourceAccess.get("account") != null){
            Map<String, Collection<String>> account =  resourceAccess.get("account");
            if(account.containsKey(REALM_NAME) ){
                Collection<String> resourceRoles = account.get(REALM_NAME);
                allRoles.addAll(resourceRoles);
            }
        }

        if(realmAccess != null && realmAccess.containsKey(REALM_NAME)){
            Collection<String> realmRoles = realmAccess.get(REALM_NAME);
            allRoles.addAll(realmRoles);
        }

        if (allRoles.isEmpty() || !Objects.equals(resourceId,jwt.getClaim("azp")) ) {
            return Set.of();
        }

        return allRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}