package com.todoapp.todo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskDataDto {
    private List<TaskDto> tasks;
    private List<TaskOrderDto> sortTasks;
}
