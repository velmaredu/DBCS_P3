package com.uva.users.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.uva.users.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByEnabled(boolean enabled);
    
}