package com.example.demo.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.*;

public  class KeycloackConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String REALM_ROLES_CLAIM_NAME = "realm_access";
    private static final String ROLES_CLAIM_NAME = "roles";
    private static final String CLIENT_ROLES_CLAIM_NAME = "resource_access";
    private static final String ROLES_PREFIX = "ROLE_";
    private static final String GROUPS_PREFIX = "GROUP_";

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt.getClaims());
        return new JwtAuthenticationToken(jwt, authorities);
    }


    private Collection<GrantedAuthority> extractAuthorities(Map<String, Object> claims) {
        if (!claims.containsKey(REALM_ROLES_CLAIM_NAME) && !claims.containsKey(CLIENT_ROLES_CLAIM_NAME)) {
            return Collections.emptyList();
        }

        Collection<GrantedAuthority> realmAuthorities = extractRealmAuthorities(claims);
        Collection<GrantedAuthority> clientAuthorities = extractClientAuthorities(claims);
        Collection<GrantedAuthority> groups = extractGroups(claims);

        List<GrantedAuthority> finalAuthorities = new ArrayList<>();
        finalAuthorities.addAll(realmAuthorities);
        finalAuthorities.addAll(clientAuthorities);
        finalAuthorities.addAll(groups);

        return finalAuthorities;
    }

    private Collection<GrantedAuthority> extractRealmAuthorities(Map<String, Object> claims) {
        // Extract roles from realm_access -> roles
        Map<String, Object> realmAccess = (Map<String, Object>) claims.get(REALM_ROLES_CLAIM_NAME);
        if (realmAccess == null || !realmAccess.containsKey(ROLES_CLAIM_NAME)) {
            return Collections.emptyList();
        }

        List<String> roles = (List<String>) realmAccess.get(ROLES_CLAIM_NAME);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(ROLES_PREFIX + role.toUpperCase()));
        }
        return authorities;
    }

    private Collection<GrantedAuthority> extractClientAuthorities(Map<String, Object> claims) {
        // Extract client roles from resource_access
        Map<String, Object> resourceAccess = (Map<String, Object>) claims.get(CLIENT_ROLES_CLAIM_NAME);
        if (resourceAccess == null) {
            return Collections.emptyList();
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (Map.Entry<String, Object> entry : resourceAccess.entrySet()) {
            Map<String, Object> clientRoles = (Map<String, Object>) entry.getValue();
            if (clientRoles.containsKey(ROLES_CLAIM_NAME)) {
                List<String> roles = (List<String>) clientRoles.get(ROLES_CLAIM_NAME);
                for (String role : roles) {
                    authorities.add(new SimpleGrantedAuthority(ROLES_PREFIX + role.toUpperCase()));
                }
            }
        }
        return authorities;
    }

    private Collection<GrantedAuthority> extractGroups(Map<String, Object> claims) {
        // Example for group extraction, if applicable
        // Replace with appropriate logic if needed
        if (!claims.containsKey("groups")) {
            return Collections.emptyList();
        }

        List<String> groups = (List<String>) claims.get("groups");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String group : groups) {
            authorities.add(new SimpleGrantedAuthority(GROUPS_PREFIX + group.toUpperCase()));
        }
        return authorities;



}}