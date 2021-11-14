package com.example.dao;

import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User mongo auto dao
 */
@Repository
public interface UserDao extends JpaRepository<User, String> {
    public User findByUsername(String username);
}
