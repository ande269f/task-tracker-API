package com.todoapp.todo.services;

import org.springframework.stereotype.Service;

import com.todoapp.todo.api.dto.UserRequestDto;
import com.todoapp.todo.enums.rowUpdateStatus;
import com.todoapp.todo.persistence.entity.User;
import com.todoapp.todo.persistence.repository.UserRepository;

import java.nio.CharBuffer;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRequestDto getUserByUsername(String username) {
        User userEntity;

        try {
            userEntity = userRepository.findFirstByUsername(username);
            if (userEntity == null) {
                return null; 
                //returnere null hvis der skal laves en ny bruger
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user with username: " + username, e);
        }

        return UserRequestDto.builder()
            .username(userEntity.getUsername())
            .password(userEntity.getPassword())
            .active(userEntity.isActive())
            .userId(userEntity.getUserId())
            .build();

    }

    public rowUpdateStatus checkUserPasswordLogin(String usernameDto, String passwordDto) {

        User user = userRepository.findFirstByUsername(usernameDto);
        String userPassword = user.getPassword();

        // hvis brugeren ikke har et kodeord
        if (userPassword == null) {
            return rowUpdateStatus.SUCCESS;
        } else 

        //hvis brugeren ikke giver et kodeord, men der er brug for et
        if (passwordDto == null) {
            return rowUpdateStatus.PASSWORD_NEEDED;
        }

        // hvis kodeordet findes og matcher brugernavnets kodeord
        if (passwordEncoder.matches(passwordDto, userPassword)) {
            return rowUpdateStatus.SUCCESS;
        } 
        else {
            return rowUpdateStatus.LOGIN_FAILED;
        }


    }

    public rowUpdateStatus createNewUserByUsername(String username) {
        
        try {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(null);
            newUser.setActive(true);
            userRepository.save(newUser);
            return rowUpdateStatus.SUCCESS;
        } catch (Exception e) {
            return rowUpdateStatus.ERROR;
        }

    }

    public rowUpdateStatus setUserPassword(String username, String password) {
        User userEntity;

        try {
            userEntity = userRepository.findFirstByUsername(username);
            if (userEntity == null) {
                //returnere null hvis der skal laves en ny bruger
                return rowUpdateStatus.USER_NOT_FOUND;
            }
            
            userEntity.setPassword(passwordEncoder.encode(CharBuffer.wrap(password)));
            userRepository.save(userEntity);
            return rowUpdateStatus.SUCCESS;


        } catch (Exception e) {
            return rowUpdateStatus.ERROR;
        }
    }

    public rowUpdateStatus deactivateUser(String username) {
        User userEntity;

        try {
            userEntity = userRepository.findFirstByUsername(username);
            if (userEntity == null) {
                //returnere null hvis der skal laves en ny bruger
                return rowUpdateStatus.USER_NOT_FOUND;
            }
            
            userEntity.setActive(false);
            userRepository.save(userEntity);
            return rowUpdateStatus.SUCCESS;


        } catch (Exception e) {
            return rowUpdateStatus.ERROR;
        }
    }

        public rowUpdateStatus deleteUser(String username) {
        User userEntity;

        try {
            userEntity = userRepository.findFirstByUsername(username);
            if (userEntity == null) {
                //returnere null hvis der skal laves en ny bruger
                return rowUpdateStatus.USER_NOT_FOUND;
            }
            userRepository.delete(userEntity);
            
            return rowUpdateStatus.SUCCESS;


        } catch (Exception e) {
            return rowUpdateStatus.ERROR;
        }
    }

}

