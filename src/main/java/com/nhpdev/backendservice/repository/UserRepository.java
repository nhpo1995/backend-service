package com.nhpdev.backendservice.repository;

import com.nhpdev.backendservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    User getUserByEmail(String email);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
}
