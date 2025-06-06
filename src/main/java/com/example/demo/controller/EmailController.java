package com.example.demo.controller;

import com.example.demo.service.Emailserviceimplement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("Email")

public class EmailController {
    private final Emailserviceimplement emailService;

    public EmailController(Emailserviceimplement emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send-email")
    public ResponseEntity<String> sendEmail() {
        try {
            String to = "jalelbensassi774@gmail.com";
            String subject = "Test Email";
            String body = "This is a test email sent from Spring Boot!";

            emailService.sendEmail(to, subject, body);
            return ResponseEntity.ok("Email sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Failed to send email: " + e.getMessage());
        }
    }
}
