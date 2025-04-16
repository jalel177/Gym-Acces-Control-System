package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.service.Roleinterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private Roleinterface roleInterface;
    @PostMapping("/add")
    public Role add(@RequestBody Role role)
    {

        return roleInterface.addRole(role);
    }
}

