package com.todoapp.todo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.todoapp.todo.api.dto.TaskDto;
import com.todoapp.todo.enums.rowUpdateStatus;
import com.todoapp.todo.persistence.entity.Task;
import com.todoapp.todo.persistence.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UnloadDataService {

    private final TaskRepository taskRepository;

    public rowUpdateStatus unloadTask(TaskDto taskDto, long userId) {
        try {
            Task task = new Task();
            task.setTaskCompleted(taskDto.getTaskCompleted());
            task.setTaskCreated(taskDto.getTaskCreated());
            task.setTaskDeleted(taskDto.getTaskDeleted());
            task.setTaskText(taskDto.getTaskText());
            task.setTaskUuid(taskDto.getTaskUuid());
            task.setUserId(userId);
            taskRepository.save(task);

        } catch (Exception e) {
            return rowUpdateStatus.ERROR;
        }

        return rowUpdateStatus.SUCCESS;


        

    }
}
