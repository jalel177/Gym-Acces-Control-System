package com.example.demo.config;

import lombok.Data;

@Data
public class RegistrationRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private  String address;
    private String password;
    private String confPassword;
}
