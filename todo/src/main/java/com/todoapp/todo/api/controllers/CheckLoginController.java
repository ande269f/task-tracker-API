package com.todoapp.todo.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.todo.enums.rowUpdateStatus;

@RestController
public class CheckLoginController {
    
    @GetMapping("checkLogin/")
    public ResponseEntity<String> checkLogin() {
        return ResponseEntity.ok("SUCCESS");
    }
}
