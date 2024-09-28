package com.app.ebook.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.ebook.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    @Query(value = "FROM User u WHERE u.email = ?1 OR u.username = ?1")
    Optional<User> findByEmailOrUsername(String identifier);
}
