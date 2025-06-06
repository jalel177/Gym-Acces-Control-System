package com.example.demo.service;

import com.example.demo.config.LoginRequest;
import com.example.demo.config.RefreshTokenrequest;
import com.example.demo.config.RegistrationRequest;
import org.springframework.http.ResponseEntity;

public interface Authinterface {



    ResponseEntity<?> signIn(LoginRequest loginRequest);

    ResponseEntity<?> signUp(RegistrationRequest registrationRequest);

    ResponseEntity<?> logout(String refreshToken);


    ResponseEntity<?> refreshToken(RefreshTokenrequest refreshTokenRequest);

    ResponseEntity<?> getUsers();

    String getAdminToken();
}
