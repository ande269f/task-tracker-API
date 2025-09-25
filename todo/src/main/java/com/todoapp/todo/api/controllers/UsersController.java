package com.todoapp.todo.api.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.todo.api.dto.LoginDto;
import com.todoapp.todo.api.dto.TaskDto;
import com.todoapp.todo.api.dto.UserDtoJwt;
import com.todoapp.todo.api.dto.UserRequestDto;
import com.todoapp.todo.configuration.UserAuthProvider;
import com.todoapp.todo.enums.rowUpdateStatus;
import com.todoapp.todo.services.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserAuthProvider userAuthProvider;
    
    private final UserService userService;

    @PostMapping("/login/")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        
        UserRequestDto userDto = userService.getUserByUsername(username);
        UserDtoJwt userDtoJwt = new UserDtoJwt();
        rowUpdateStatus loginStatus = rowUpdateStatus.ERROR;

        // hvis brugeren ikke findes
        if (userDto == null) {
            return ResponseEntity.ok(rowUpdateStatus.USER_NOT_FOUND);
        }
        

        loginStatus = userService.checkUserPasswordLogin(userDto.getUsername(), password);

        // hvis kodeordet er korrekt, lav jwt token
        if (loginStatus.equals(rowUpdateStatus.SUCCESS)) {
            userDtoJwt.setToken(userAuthProvider.createToken(userDto));
            return ResponseEntity.ok(userDtoJwt);
        }
        else {
            return ResponseEntity.ok(loginStatus.toString());
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
