package com.todoapp.todo.api.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.todo.api.dto.UserRequestDto;
import com.todoapp.todo.enums.rowUpdateStatus;
import com.todoapp.todo.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;




@RestController
@RequestMapping("/users")
public class UsersController {
    
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("getUser/{username}")
    public ResponseEntity<UserRequestDto> getUserByUsername(@PathVariable String username) {
        // "userRequestDto" = det objekt og objekttype som jeg vil sende afsted
        // "userService.getUserDtoById" = den operation der returnere objektet som gemmes
        // i "userRequestDto".
        UserRequestDto userRequestDto = userService.getUserDtoByUsername(username);

        if (userRequestDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userRequestDto);
    }

    @PostMapping("createNewUser/{username}")
    public ResponseEntity<String> createNewUserByUsername(@PathVariable String username) {
        userService.createNewUserByUsername(username);
        return new ResponseEntity<>("new user created with username: " + username, HttpStatus.OK);
    }


    @PostMapping("setUserPassword/{username}/{password}")
    public ResponseEntity<String> setUserPassword(@PathVariable String username, @PathVariable String password) {
        rowUpdateStatus status = userService.setUserPassword(username, password);
        
            switch (status) {
        case SUCCESS:
            return ResponseEntity
                    .ok("Password updated successfully for user: " + username);

        case USER_NOT_FOUND:
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found: " + username);

        case ERROR:
        default:
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating password for: " + username);
    }
    }
    
    
    
}
