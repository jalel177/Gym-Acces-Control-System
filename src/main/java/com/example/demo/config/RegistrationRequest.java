package com.example.demo.config;

import lombok.Data;

@Data
public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private  String address;
    private String password;
    private String confPassword;
}
