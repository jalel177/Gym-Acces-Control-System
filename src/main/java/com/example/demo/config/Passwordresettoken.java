package com.example.demo.config;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_token")
public class Passwordresettoken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token", nullable = false, unique = true) // Mapping explicite
    private String token;


    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    public Passwordresettoken() {}

    public Passwordresettoken(String token, String userId, LocalDateTime expirationDate) {
        this.token= token;
        this.userId = userId;
        this.expirationDate = expirationDate;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public LocalDateTime getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDateTime expirationDate) { this.expirationDate = expirationDate; }
}