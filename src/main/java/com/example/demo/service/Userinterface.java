package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface Userinterface {
    User addUser(User user);

    void deleteUser(String id);

    List<User> addListUsers(List<User> listusers);

    String addUserWTCP(User user);

    String addUserWTUN(User user);


    List<User> getAllusers();


    User getUser(String id);

    User getuserByUsername(String un);

    List<User> getUsersSW(String un);

    List<User> getUsersByEmail(String un);


    User updateUser(String id, User user);
}
