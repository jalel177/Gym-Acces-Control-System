package com.example.demo.service;

import com.example.demo.DTO.UserUpdatedto;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class Keycloakserviceimplement {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceimplement.class);
    @Autowired
    private UserRepository userRepository;

@Transactional
    public User getCurrentUser(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String keycloakId = jwt.getSubject();

        Optional<User> existingUser = userRepository.findById(keycloakId);

        if (existingUser.isPresent()) {
            return existingUser.get();
        } else {
            try {
                // Créer un nouvel utilisateur si n'existe pas
                User newUser = new User();
                newUser.setUserid(keycloakId);
                newUser.setUsername(jwt.getClaim("preferred_username"));
                newUser.setEmail(jwt.getClaim("email"));
                newUser.setFirstName(jwt.getClaim("given_name"));
                newUser.setLastName(jwt.getClaim("family_name"));
                newUser.setAddress(jwt.getClaim("address"));


                return userRepository.save(newUser);
            } catch (Exception e) {
                logger.error("Error creating new user for keycloakId: " + keycloakId, e);
                throw e;

            }
        }
    }
    public void updateKeycloakUserProfile(String userId, UserUpdatedto.UserUpdateDTO dto) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8086")
                .realm("master")
                .clientId("admin-cli")
                .username("admin")
                .password("admin")
                .build();

        UserResource userResource = keycloak.realm("master").users().get(userId);

        UserRepresentation user = userResource.toRepresentation();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        userResource.update(user);

        // ➕ Mise à jour du mot de passe
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            CredentialRepresentation newPassword = new CredentialRepresentation();
            newPassword.setType(CredentialRepresentation.PASSWORD);
            newPassword.setValue(dto.getPassword());
            newPassword.setTemporary(false); // false = définitif

            userResource.resetPassword(newPassword);
        }
    }

}

