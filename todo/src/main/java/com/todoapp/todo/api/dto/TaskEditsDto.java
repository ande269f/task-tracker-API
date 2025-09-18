package com.todoapp.todo.api.dto;


import java.time.LocalDateTime;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEditsDto {
    @Id
    private String editUuid;  
    private LocalDateTime dateEdits;
    private LocalDateTime taskDeleted;
    private String taskUuid;
    private String taskText;
    private boolean taskCompleted;
    private long userID;

}
