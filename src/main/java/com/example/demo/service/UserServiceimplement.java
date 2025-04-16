package com.example.demo.service;

import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceimplement implements Userinterface {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional
    public User addUser(User user) {
            return userRepository.save(user);
        }



    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> addListUsers(List<User> listusers) {
        return userRepository.saveAll(listusers);
    }
    @Override
    public String addUserWTCP(User user)
    {
        String ch ="";
        if(user.getPassword().equals(user.getConfPassword()))
        {
            userRepository.save(user);
            ch="user added successfuly";

        }
        else
        {
            ch="check conf password!" ;
        }
        return ch ;
    }

    @Override
    public String addUserWTUN(User user) {
        String ch="";
        if(userRepository.existsByUsername(user.getUsername()))
        {
            ch=" user already exists";
        }else {
            userRepository.save(user);
            ch="user added !!" ;
        }
        return ch;
    }





    @Override
    public List<User> getAllusers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String id) {
        return userRepository.findById(id).orElse(null);

    }

    @Override
    public User getuserByUsername(String un) {
        return userRepository.finduserByUsername(un);
    }

    @Override
    public List<User> getUsersSW(String un) {
        return userRepository.getUserSW(un);
    }

    @Override
    public List<User> getUsersByEmail(String un) {
        return userRepository.getUsersByEmail(un);
    }

    @Override
    public User updateUser(String id,User user) {
        user = userRepository.findById(id).get();
        user.setFirstName(user.getFirstName());
        user.setLastName((user.getLastName()));
        return userRepository.save(user);
    }




}

