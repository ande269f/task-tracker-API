package com.todoapp.todo.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    private String taskUuid;
    private long userId;
    private String taskText;
    private Boolean taskCompleted;
    private String taskDeleted;
    private String taskCreated;
    
}
