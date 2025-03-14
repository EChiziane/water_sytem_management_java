package com.api.water_sytem_management_java.services;


import com.api.water_sytem_management_java.controllers.dtos.UserOutPut;
import com.api.water_sytem_management_java.models.User;
import com.api.water_sytem_management_java.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }


    public List<UserOutPut> getUsers() {
      return  userRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
              .stream()
              .map(this::mapToUserOutPut)
              .collect(Collectors.toList());
    }


    private UserOutPut mapToUserOutPut(User user) {
        return user.userOutPut(user);
    }
}
