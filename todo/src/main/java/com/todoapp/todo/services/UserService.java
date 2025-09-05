package com.todoapp.todo.services;

import org.springframework.stereotype.Service;

import com.todoapp.todo.api.dto.UserRequestDto;
import com.todoapp.todo.persistence.entity.User;
import com.todoapp.todo.persistence.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRequestDto getUserDtoById(Long userId) {
        User userEntity = userRepository.findFirstByUserId(userId);

        //bygger et objekt af typen UserRequestDto som beskrevet i dto mappen
        //bruger builder til at bygge objektet i overensstemmelse med entity/User
        return UserRequestDto.builder()
            .username(userEntity.getUsername())
            .password(userEntity.getPassword())
            .active(userEntity.getActive())
            .userId(userEntity.getUserId())
            .build();
    }

}
