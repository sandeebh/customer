package com.arshad.webservice.UserManagement.controllers;

import com.arshad.webservice.UserManagement.beans.User;
import com.arshad.webservice.UserManagement.services.UserService;
import com.arshad.webservice.UserManagement.utils.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "v1/user")
public class UserController {

    @Autowired
    private UserService userServiceImpl;

    @GetMapping(path = "/users")
    public List<User> getAllUsers(){
        return userServiceImpl.getAllUsers();
    }

    @GetMapping(path = "/users/{id}")
    public User getUser(@PathVariable int id){
        User user = userServiceImpl.getUserByID(id);
        if(user == null){
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @PostMapping(path = "/users")
    public ResponseEntity addUser(@RequestBody User user){
        userServiceImpl.addUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

}