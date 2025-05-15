package com.api.water_sytem_management_java.services;


import com.api.water_sytem_management_java.controllers.dtos.UserOutPut;
import com.api.water_sytem_management_java.models.user.User;
import com.api.water_sytem_management_java.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

    public List<UserOutPut> getUsers() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).
                stream()
                .map(this::mapToUserOutput)
                .collect(Collectors.toList());
    }


    private UserOutPut mapToUserOutput(User user) {
        return user.UserOutPut();
    }


}
