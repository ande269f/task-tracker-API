package com.todoapp.todo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskOrderUpdateDto {
    private TaskOrderDto from;
    private TaskOrderDto to;
}