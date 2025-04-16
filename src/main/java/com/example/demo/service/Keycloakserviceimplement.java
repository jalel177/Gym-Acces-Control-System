package com.example.demo.service;

import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class Keycloakserviceimplement {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceimplement.class);
    @Autowired
    private UserRepository userRepository;

    /**

     Récupère ou crée un utilisateur basé sur les informations du token JWT Keycloak*/@Transactional
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
    }

