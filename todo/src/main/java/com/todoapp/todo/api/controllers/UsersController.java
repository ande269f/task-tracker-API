package com.todoapp.todo.api.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.todo.api.dto.UserRequestDto;
import com.todoapp.todo.enums.rowUpdateStatus;
import com.todoapp.todo.persistence.entity.User;
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

    @GetMapping("/getUser/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username, @RequestParam(required = false) String password) {
        
        User user = userService.getUserByUsername(username);

        // hvis brugeren ikke findes
        if (user == null) {
            return ResponseEntity.ok(rowUpdateStatus.USER_NOT_FOUND);
        }

        rowUpdateStatus loginStatus = userService.checkUserPasswordLogin(user, password);

        if (loginStatus.equals(rowUpdateStatus.SUCCESS)) {
            UserRequestDto userDto = userService.setUserDto(user);
            return ResponseEntity.ok(userDto);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(loginStatus.toString());
        }



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
