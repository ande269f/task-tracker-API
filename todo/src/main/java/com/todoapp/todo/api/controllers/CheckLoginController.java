package com.todoapp.todo.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.todo.enums.rowUpdateStatus;

@RestController
public class CheckLoginController {
    
    @GetMapping("checkLogin/")
    public ResponseEntity<String> checkLogin(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.ok("NOT_LOGGED_IN");
        }
        return ResponseEntity.ok("SUCCESS");
    }
}
