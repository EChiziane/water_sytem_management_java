package com.api.water_sytem_management_java.controllers;


import com.api.water_sytem_management_java.controllers.dtos.UserOutPut;
import com.api.water_sytem_management_java.models.User;
import com.api.water_sytem_management_java.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRegisterInput userRegisterInput) {
User user= userRegisterInput.toUser();
User savedUser= userService.createUser(user);
return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);


    }



    @GetMapping
    public ResponseEntity<List<UserOutPut>> getUsers() {
     return  ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

}
