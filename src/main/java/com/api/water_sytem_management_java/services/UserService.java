package com.api.water_sytem_management_java.services;

import com.api.water_sytem_management_java.controllers.dtos.UserOutPut;
import com.api.water_sytem_management_java.models.User;
import com.api.water_sytem_management_java.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<UserOutPut> getUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(this::mapToUserOutPut)
                .collect(Collectors.toList());
    }

    public Optional<UserOutPut> getUserById(UUID id) {
        return userRepository.findById(id)
                .map(this::mapToUserOutPut);
    }


    public boolean authenticate(String email, String password) {
        return userRepository.findByEmail(email).
                map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }

/*    @Transactional
    public Optional<User> updateUser(UUID id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            user.setRole(updatedUser.getRole());
            user.setUpdatedAt(updatedUser.getUpdatedAt());
            return userRepository.save(user);
        });
    }*/

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    private UserOutPut mapToUserOutPut(User user) {
        return user.userOutPut(user);
    }


}
