package com.example.demo.controller;

import com.example.demo.config.LoginRequest;
import com.example.demo.config.RefreshTokenrequest;
import com.example.demo.config.RegistrationRequest;
import com.example.demo.service.Authinterface;
import com.example.demo.service.Passwordresetserviceimplement;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private Authinterface authinterface;
    @Autowired
    private Passwordresetserviceimplement passwordresetserviceimplement;
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

@PostMapping("/forgot-password")
public ResponseEntity<?> forgotPassword(@RequestParam String email) {
    try {
        passwordresetserviceimplement.createPasswordResetToken(email);
        return ResponseEntity.ok("Email de réinitialisation envoyé si l'email est enregistré.");
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam("token") String token,
            @RequestParam(value = "newPassword",required = false) String newPassword) {
        try {
            // if newPassword is null or blank → verification only
            if (newPassword == null || newPassword.isBlank()) {
                passwordresetserviceimplement.verifyCode(token);
                return ResponseEntity
                        .ok("Code valide. Vous pouvez maintenant réinitialiser votre mot de passe.");
            }

            // otherwise, full reset
            passwordresetserviceimplement.resetPassword(token, newPassword);
            return ResponseEntity
                    .ok("Mot de passe réinitialisé avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}

