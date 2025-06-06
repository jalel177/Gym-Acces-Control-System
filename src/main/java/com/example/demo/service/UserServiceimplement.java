package com.example.demo.service;

import com.example.demo.DTO.UserUpdatedto;
import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.*;
import jakarta.transaction.Transactional;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceimplement implements Userinterface {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional
    public User addUser(User user) {
            return userRepository.save(user);
        }

    private final Authinterface authService; // Use interface for better practice
    private final RestTemplate restTemplate;

    // Constructor injection
    public UserServiceimplement(Authinterface authService, RestTemplate restTemplate) {
        this.authService = authService;
        this.restTemplate = restTemplate;
    }

    @Override
    public void deleteUser(String userid) {
            // Suppression dans Keycloak
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl("http://localhost:8086")
                    .realm("master")
                    .clientId("admin-cli")
                    .username("admin")
                    .password("admin")
                    .build();

            keycloak.realm("master").users().get(userid).remove();

            // Suppression dans la base de données
        userRepository.deleteById(userid);
    }

    @Override
    public List<User> addListUsers(List<User> listusers) {
        return userRepository.saveAll(listusers);
    }
    @Override
    public String addUserWTCP(User user)
    {
        String ch ="";
        if(user.getPassword().equals(user.getConfPassword()))
        {
            userRepository.save(user);
            ch="user added successfuly";

        }
        else
        {
            ch="check conf password!" ;
        }
        return ch ;
    }

    @Override
    public String addUserWTUN(User user) {
        String ch="";
        if(userRepository.existsByUsername(user.getUsername()))
        {
            ch=" user already exists";
        }else {
            userRepository.save(user);
            ch="user added !!" ;
        }
        return ch;
    }





    @Override
    public List<User> getAllusers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String id) {
        return userRepository.findById(id).orElse(null);

    }

    @Override
    public User getuserByUsername(String un) {
        return userRepository.finduserByUsername(un);
    }

    @Override
    public List<User> getUsersSW(String un) {
        return userRepository.getUserSW(un);
    }

    @Override
    public List<User> getUsersByEmail(String un) {
        return userRepository.getUsersByEmail(un);
    }
@Override
    public User updateUser(String id, UserUpdatedto.UserUpdateDTO userUpdateDTO) {
        // Fetch the existing user or throw an exception if not found
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Update fields from DTO
        existingUser.setFirstName(userUpdateDTO.getFirstName());
        existingUser.setLastName(userUpdateDTO.getLastName());
        existingUser.setEmail(userUpdateDTO.getEmail());
    existingUser.setUsername(userUpdateDTO.getUsername());
    existingUser.setPassword(userUpdateDTO.getPassword());
        existingUser.setAddress(userUpdateDTO.getAddress());
    User updatedUser = userRepository.save(existingUser);

    // 2. Update Keycloak
    updateKeycloakUser(existingUser, userUpdateDTO);

    return updatedUser;
    }
    private void updateKeycloakUser(User user, UserUpdatedto.UserUpdateDTO dto) {
        try {
            String adminToken = authService.getAdminToken();
            if (adminToken == null || user.getUserid() == null) {
                throw new RuntimeException("Missing authentication or user ID");
            }

            String url = "http://localhost:8086/admin/realms/master/users/" + user.getUserid();

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(
                    Map.of(
                            "firstName", dto.getFirstName(),
                            "lastName", dto.getLastName(),
                            "email", dto.getEmail(),
                            "username", dto.getUsername()
                    ),
                    createHeaders(adminToken)
            );

            restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);
        } catch (Exception e) {
            throw new RuntimeException("Keycloak update failed: " + e.getMessage());
        }
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }



}

