package com.todoapp.todo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ErrorDto {

    private String message;
    
}

// 43:11 - https://www.youtube.com/watch?v=bqFjrhRrvy8&ab_channel=TheDevWorld-bySergioLema
