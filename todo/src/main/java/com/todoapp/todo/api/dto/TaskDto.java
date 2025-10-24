package com.todoapp.todo.api.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String taskUuid;
    private String taskText;
    private Boolean taskCompleted;
    private String taskDeleted;
    private String taskCreated;
}
