package com.todoapp.todo.services;

import org.springframework.stereotype.Service;

import com.todoapp.todo.api.dto.UserRequestDto;
import com.todoapp.todo.enums.rowUpdateStatus;
import com.todoapp.todo.persistence.entity.User;
import com.todoapp.todo.persistence.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRequestDto getUserDtoByUsername(String username) {
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
    
        //bygger et objekt af typen UserRequestDto som beskrevet i dto mappen
        //bruger builder til at bygge objektet i overensstemmelse med entity/User
        return UserRequestDto.builder()
            .username(userEntity.getUsername())
            .password(userEntity.getPassword())
            .active(userEntity.isActive())
            .userId(userEntity.getUserId())
            .build();
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
            
            userEntity.setPassword(password);
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

