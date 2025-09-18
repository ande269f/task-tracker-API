package com.todoapp.todo.persistence.entity;

import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="task_edits")
public class TaskEdits {
    @Id
    private String editUuid;  
    private LocalDateTime dateEdits;
    private LocalDateTime taskDeleted;
    private String taskUuid;
    private String taskText;
    private boolean taskCompleted;

}
