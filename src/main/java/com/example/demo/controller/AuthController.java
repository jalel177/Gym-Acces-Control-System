package com.example.demo.controller;

import com.example.demo.config.LoginRequest;
import com.example.demo.config.RefreshTokenrequest;
import com.example.demo.config.RegistrationRequest;
import com.example.demo.service.Authinterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private Authinterface authinterface;
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest loginRequest) {
        return authinterface.signIn(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody RegistrationRequest registrationRequest) {
        return authinterface.signUp(registrationRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String refreshToken) {
        return authinterface.logout(refreshToken);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenrequest refreshTokenRequest) {
        return authinterface.refreshToken(refreshTokenRequest);
    }

    @GetMapping("/getusers")
    public ResponseEntity<?> getUsers() {
        return authinterface.getUsers();
    }
}
