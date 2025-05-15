package com.api.water_sytem_management_java.controllers;


import com.api.water_sytem_management_java.controllers.dtos.AuthenticationDTO;
import com.api.water_sytem_management_java.controllers.dtos.LoginResponseDTO;
import com.api.water_sytem_management_java.controllers.dtos.RegisterDTO;
import com.api.water_sytem_management_java.controllers.dtos.UserOutPut;
import com.api.water_sytem_management_java.models.user.User;
import com.api.water_sytem_management_java.repositories.UserRepository;
import com.api.water_sytem_management_java.services.AuthorizationService;
import com.api.water_sytem_management_java.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("auth")
public class AuthenticationController {
    private final AuthorizationService authorizationService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    public AuthenticationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
        User newUser = data.toUser();
        this.repository.save(newUser);
        return ResponseEntity.ok().body(newUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserOutPut>> getAllUsers() {
        List<UserOutPut> users = authorizationService.getUsers();
        return ResponseEntity.ok().body(users);
    }


}