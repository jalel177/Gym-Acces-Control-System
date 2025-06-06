package com.example.demo.service;

import com.example.demo.DTO.UserUpdatedto;
import com.example.demo.config.Passwordresettoken;
import com.example.demo.dao.Passwordresettokenrepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.Random;
@Service
public class Passwordresetserviceimplement {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Passwordresettokenrepository tokenRepository;

    @Autowired
    private Emailserviceimplement emailService;

    @Autowired
    private Keycloakserviceimplement keycloakUserService;

    @Transactional
    public void createPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Aucun utilisateur trouvé avec cet email.");
        }

        // Supprimer anciens tokens
        tokenRepository.deleteByUserId(user.getUserid());

        // Generate 6-digit numeric code
        Random random = new Random();
        String token= String.format("%06d", random.nextInt(999999)); // 000000 to 999999
        LocalDateTime expiration = LocalDateTime.now().plusHours(1);

        Passwordresettoken resetToken = new Passwordresettoken(token, user.getUserid(), expiration);
        tokenRepository.save(resetToken);

        // Update email message
        String subject = "Code de réinitialisation de mot de passe";
        String message = "<p>Bonjour " + user.getFirstName() + ",</p>"
                + "<p>Votre code de réinitialisation est : <strong>" +token + "</strong></p>"
                + "<p>Ce code expirera dans 1 heure.</p>"
                + "<p>Entrez ce code dans l'application pour continuer.</p>";

        // Envoyer l'email

        emailService.sendEmail(user.getEmail(), subject, message);
    }

    public void verifyCode(String token) {
        Passwordresettoken prt = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Code invalide."));
        if (prt.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Le code a expiré.");
        }}
    public void resetPassword(String token, String newPassword) {
        verifyCode(token);

        // 2) lookup again (safe because verifyCode already guaranteed it's present)
        Passwordresettoken prt = tokenRepository.findByToken(token).get();

        // 3) find user
        User user = userRepository.findById(prt.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé."));

        // 4) update Keycloak
        keycloakUserService.updateKeycloakUserProfile(
                user.getUserid(),
                new UserUpdatedto.UserUpdateDTO()
                        .setFirstName(user.getFirstName())
                        .setLastName(user.getLastName())
                        .setEmail(user.getEmail())
                        .setPassword(newPassword)
        );

        // 5) invalidate token
        tokenRepository.delete(prt);
    }
}

