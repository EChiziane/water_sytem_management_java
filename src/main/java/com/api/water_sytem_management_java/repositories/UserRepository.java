package com.api.water_sytem_management_java.repositories;


import com.api.water_sytem_management_java.models.user.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);
    List<User> findAll(Sort sort);
}