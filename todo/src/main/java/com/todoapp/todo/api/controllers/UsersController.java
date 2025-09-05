package com.todoapp.todo.api.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.todo.api.dto.UserRequestDto;
import com.todoapp.todo.services.UserService;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/users")
public class UsersController {
    
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRequestDto> getUserByUserId(@PathVariable Long userId) {
        // "userRequestDto" = det objekt og objekttype som jeg vil sende afsted
        // "userService.getUserDtoById" = den operation der returnere objektet som gemmes
        // i "userRequestDto".
        UserRequestDto userRequestDto = userService.getUserDtoById(userId);
        return ResponseEntity.ok(userRequestDto);
    }
    
}
