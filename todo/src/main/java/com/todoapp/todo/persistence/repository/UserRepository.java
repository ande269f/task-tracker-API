package com.todoapp.todo.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoapp.todo.persistence.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUserId(Long userId);
    
    User findFirstByUserId(Long userId);
}
