package com.todoapp.todo.api.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.todo.api.dto.UserRequestDto;
import com.todoapp.todo.enums.rowUpdateStatus;
import com.todoapp.todo.services.UserService;

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

    @GetMapping("/getUser/{username}/{password}")
    public ResponseEntity<UserRequestDto> getUserByUsername(@PathVariable String username, @PathVariable String password) {
        UserRequestDto userRequestDto = userService.getUserDtoByUsername(username);
        if (userRequestDto == null) {
            return ResponseEntity.noContent().build();
        }
        
        //hvis password ikke er sat
        if (password.equals("null") && userRequestDto.getPassword() == null) {
            return ResponseEntity.ok(userRequestDto);
        }

        //hvis password er sat og det er det rigtige
        var thePassword = userRequestDto.getPassword();
        if (thePassword.equals(password) && !thePassword.equals("null")) {
            return ResponseEntity.ok(userRequestDto);
        }
        return ResponseEntity.ok(null);
    }

    @PostMapping("/createNewUser/{username}")
    public ResponseEntity<String> createNewUserByUsername(@PathVariable String username) {
        rowUpdateStatus status = userService.createNewUserByUsername(username);
        return ResponseEntity.ok(status.toString());
    }


    @PostMapping("/setUserPassword/{username}/{password}")
    public ResponseEntity<String> setUserPassword(@PathVariable String username, @PathVariable String password) {
        rowUpdateStatus status = userService.setUserPassword(username, password);
        
        return ResponseEntity.ok(status.toString());
    }

    @PostMapping("/deactivateUser/{username}")
    public ResponseEntity<String> deactivateUser(@PathVariable String username) {
        rowUpdateStatus status = userService.deactivateUser(username);
        
        return ResponseEntity.ok(status.toString());
    }

    @PostMapping("/deleteUser/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        rowUpdateStatus status = userService.deleteUser(username);
        
        return ResponseEntity.ok(status.toString());
    }


    
    
    
}
