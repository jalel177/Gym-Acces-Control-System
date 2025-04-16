package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.Userinterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    Userinterface userInterface;

    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getUser() {
        Map<String,Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", "User details here");

        return ResponseEntity.ok(response);
    }
    @PostMapping("/adduser")
    @Transactional
    public User addUser(@RequestBody User user)
    {

        return userInterface.addUser(user);
    }




    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable String id){
         userInterface.deleteUser(id);
    }
    @DeleteMapping("/deleteuser")
    public void deleteUsers(@RequestParam String id){
        userInterface.deleteUser(id);
    }
    @PostMapping("addlistusers")
    public List<User> addlistusers(@RequestBody List<User> users)
    {
        return userInterface.addListUsers(users);
    }
    @PostMapping("/addwithconfpassword")
    public String addUserWTCP(@RequestBody User user)
    {

        return userInterface.addUserWTCP(user);
    }

    @PostMapping("/adduserWTUN")
    public String addUserWTUN(@RequestBody User user)
    {
        return userInterface.addUserWTUN(user);
    }
    @PatchMapping("/updateuser/{ids}")
    public User updateuser(@PathVariable("ids")String id,@RequestBody User user)
    {return userInterface.updateUser(id,user);}
    @GetMapping("/getAllusers")
    public   List<User> getAllUsers()
    {
        return userInterface.getAllusers();
    }
    @GetMapping("getUserById/{id}")
    public User getUserById(@PathVariable String id)
    {
        return userInterface.getUser(id);
    }
    @GetMapping("getUserByUN/{un}")
    public User getUserByUsername(@PathVariable String un)
    {
        return userInterface.getuserByUsername(un);
    }
    @GetMapping("getUsersSWuN/{un}")
    public   List<User> getUsersSW(@PathVariable String un)
    {
        return userInterface.getUsersSW(un);
    }
    @GetMapping("/getUsersByEmail")
    public  List<User> getUsersByEmail(@RequestParam String email)
    {
        return userInterface.getUsersByEmail(email);
    }
}

