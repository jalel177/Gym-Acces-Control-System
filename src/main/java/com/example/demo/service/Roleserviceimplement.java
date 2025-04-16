package com.example.demo.service;

import com.example.demo.dao.RoleRepository;
import com.example.demo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Roleserviceimplement implements Roleinterface {
    @Autowired
    private RoleRepository roleRepo;

    @Override
    public Role addRole(Role role) {
        return  roleRepo.save(role);
    }



}

