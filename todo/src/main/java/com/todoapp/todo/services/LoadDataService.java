package com.todoapp.todo.services;

import org.springframework.stereotype.Service;

import com.todoapp.todo.api.dto.TaskDto;
import com.todoapp.todo.api.dto.UserRequestDto;
import com.todoapp.todo.enums.rowUpdateStatus;
import com.todoapp.todo.persistence.entity.Task;
import com.todoapp.todo.persistence.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.core.Authentication;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoadDataService {
        private final TaskRepository taskRepository;

        public List<TaskDto> loadTasks(Authentication authentication) {
            UserRequestDto user = (UserRequestDto) authentication.getPrincipal();
            long userId = user.getUserId();
            List<Task> tasks = taskRepository.findAllByUserId(userId);
            List<TaskDto> tasksDto = new ArrayList<>();
            for (Task task: tasks) {
                TaskDto taskDto = TaskDto.builder()
                .taskText(task.getTaskText())
                .taskCompleted(task.getTaskCompleted())
                .taskDeleted(task.getTaskDeleted())
                .taskUuid(task.getTaskUuid())
                .taskCreated(task.getTaskCreated())
                .build();

                tasksDto.add(taskDto);

            }

            return tasksDto;
        }
}
