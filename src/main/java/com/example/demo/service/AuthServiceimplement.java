package com.example.demo.service;

import com.example.demo.config.LoginRequest;
import com.example.demo.config.RefreshTokenrequest;
import com.example.demo.config.RegistrationRequest;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.*;


@Service
public class AuthServiceimplement implements Authinterface {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceimplement.class);
    private final RestTemplate restTemplate;
    private final String keycloakBaseUrl;
    private final String keycloakRealm;
    private final String clientId;
    private final String clientSecret;




    @Autowired
    private UserRepository userRepository;


    public AuthServiceimplement(
            RestTemplate restTemplate,
            @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}") String issuerUri,
            @Value("${spring.security.oauth2.client.registration.demo.client-id}") String clientId,
            @Value("${spring.security.oauth2.client.registration.demo.client-secret}") String clientSecret) {
        if (issuerUri.endsWith("/")) {
            issuerUri = issuerUri.substring(0, issuerUri.length() - 1);
        }
        this.restTemplate = restTemplate;
        this.keycloakBaseUrl = issuerUri.substring(0, issuerUri.indexOf("/realms/"));
        this.keycloakRealm = issuerUri.substring(issuerUri.lastIndexOf("/") + 1);
        this.clientId = clientId;
        this.clientSecret = clientSecret;}

    @Override
    public ResponseEntity<?> signIn(LoginRequest loginRequest) {
        String tokenEndpoint = keycloakBaseUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("username", loginRequest.getUsername());
        formData.add("password", loginRequest.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, request, Map.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials", "message", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> signUp(RegistrationRequest registrationRequest) {
        String userEndpoint = keycloakBaseUrl + "/admin/realms/" + keycloakRealm + "/users";
        String adminToken = getAdminToken();

        if (adminToken == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get admin token"));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);

        Map<String, Object> userRepresentation = Map.of(
                "firstName", registrationRequest.getFirstName(),
                "lastName", registrationRequest.getLastName(),
                "username", registrationRequest.getUsername(),
                "email", registrationRequest.getEmail(),
                "enabled", true,
                "attributes", Map.of(
                        "address", registrationRequest.getAddress()),
                "credentials", new Object[]{
                        Map.of(
                                "type", "password",
                                "value", registrationRequest.getPassword(),
                                "temporary", false
                        )
                }

        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userRepresentation, headers);

        try {
            restTemplate.postForEntity(userEndpoint, request, Void.class);
            String stringuserId = getUserIdByUsername(registrationRequest.getUsername(), adminToken);

            if (stringuserId == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "User created but failed to retrieve user ID"));
            }

            // Étape 3: Attribuer le rôle CLIENT à l'utilisateur
            boolean roleAdded = addRoleToUser(stringuserId, "MEMBER", adminToken);
            if (!roleAdded) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "User created but failed to assign CLIENT role"));
            }


            User newUser = new User();
            newUser.setUserid(stringuserId);
            newUser.setUsername(registrationRequest.getUsername());
            newUser.setEmail(registrationRequest.getEmail());
            newUser.setFirstName(registrationRequest.getFirstName());
            newUser.setLastName(registrationRequest.getLastName());

            userRepository.save(newUser);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "User created successfully with MEMBER role"));
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Invalid user ID format", "message", e.getMessage()));
        }

        }


    @Override
    public ResponseEntity<?> logout(String refreshToken) {
        String logoutEndpoint = keycloakBaseUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/logout";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        try {
            restTemplate.postForEntity(logoutEndpoint, request, Void.class);
            return ResponseEntity.ok(Map.of("message", "Logout successful"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Logout failed", "message", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> refreshToken(RefreshTokenrequest refreshTokenRequest) {

        String tokenEndpoint = keycloakBaseUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("refresh_token", refreshTokenRequest.getRefreshToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, request, Map.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid refresh token", "message", e.getMessage()));
        }
    }
    @Override
    public ResponseEntity<?> getUsers() {
        String userEndpoint = keycloakBaseUrl + "/admin/realms/" + keycloakRealm + "/users";

        // Obtenir un token admin
        String adminToken = getAdminToken();
        if (adminToken == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get admin token"));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Object[]> response = restTemplate.exchange(
                    userEndpoint,
                    HttpMethod.GET,
                    request,
                    Object[].class
            );
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Failed to fetch users", "message", e.getMessage()));
        }
    }


    private boolean addRoleToUser(String userId, String roleName, String adminToken) {
        // Étape 1: Obtenir l'ID du rôle
        String roleId = getRoleId(roleName, adminToken);
        if (roleId == null) {
            return false;
        }

        // Étape 2: Ajouter le rôle à l'utilisateur
        String roleEndpoint = keycloakBaseUrl + "/admin/realms/" + keycloakRealm + "/users/" + userId + "/role-mappings/realm";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);

        List<Map<String, Object>> roles = List.of(
                Map.of(
                        "id", roleId,
                        "name", roleName
                )
        );

        HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(roles, headers);

        try {
            restTemplate.exchange(
                    roleEndpoint,
                    HttpMethod.POST,
                    request,
                    Void.class
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private String getRoleId(String roleName, String adminToken) {
        String rolesEndpoint = keycloakBaseUrl + "/admin/realms/" + keycloakRealm + "/roles";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<List> response = restTemplate.exchange(
                    rolesEndpoint,
                    HttpMethod.GET,
                    request,
                    List.class
            );

            List<Map<String, Object>> roles = response.getBody();
            if (roles != null) {
                for (Map<String, Object> role : roles) {
                    if (roleName.equals(role.get("name"))) {
                        return (String) role.get("id");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
    @Override
    public String getAdminToken() {
        String tokenEndpoint = keycloakBaseUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, request, Map.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
               String accestoken= (String) response.getBody().get("access_token");
                logger.debug("Successfully retrieved admin token.");
               return accestoken;
            }else{
                logger.error("Error fetching admin token: Received status {} with body {}",
                        response.getStatusCode(), response.getBody());
                return null;}
        }catch(Exception e){
                logger.error("Exception occurred while fetching admin token: ", e);
                return null;
        }
    }


    private String getUserIdByUsername(String username, String adminToken) {
        String searchEndpoint = keycloakBaseUrl + "/admin/realms/" + keycloakRealm + "/users?username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<List> response = restTemplate.exchange(searchEndpoint, HttpMethod.GET, request, List.class);
            List<Map<String, Object>> users = response.getBody();
            if (users != null && !users.isEmpty()) {
                return (String) users.get(0).get("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
