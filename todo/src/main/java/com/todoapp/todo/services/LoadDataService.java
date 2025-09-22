package com.todoapp.todo.services;

import org.springframework.stereotype.Service;

import com.todoapp.todo.api.dto.TaskDto;
import com.todoapp.todo.api.dto.TaskEditDto;
import com.todoapp.todo.api.dto.TaskOrderDto;
import com.todoapp.todo.api.dto.UserRequestDto;
import com.todoapp.todo.enums.rowUpdateStatus;
import com.todoapp.todo.persistence.entity.Task;
import com.todoapp.todo.persistence.entity.TaskEdit;
import com.todoapp.todo.persistence.entity.TaskOrder;
import com.todoapp.todo.persistence.repository.TaskEditRepository;
import com.todoapp.todo.persistence.repository.TaskOrderRepository;
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
        private final TaskEditRepository taskEditRepository;
        private final TaskOrderRepository taskOrderRepository;

        public long fetchUserId(Authentication authentication) {
            UserRequestDto user = (UserRequestDto) authentication.getPrincipal();
            long userId = user.getUserId();
            return userId;
        }

        public List<TaskDto> loadTasks(long userId) {

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

        public List<TaskEditDto> loadTaskEdits(String taskUuid) {
            List<TaskEdit> taskEdits =  taskEditRepository.findAllByTaskUuid(taskUuid);
            List<TaskEditDto> taskEditsDto = new ArrayList<>();

        for (TaskEdit taskEdit : taskEdits) {
            TaskEditDto taskEditDto = TaskEditDto.builder()
                    .taskEditsUuid(taskEdit.getEditUuid())
                    .dateEdited(taskEdit.getDateEdited())
                    .taskDeleted(taskEdit.getTaskDeleted())
                    .taskUuid(taskEdit.getTaskUuid())
                    .taskText(taskEdit.getTaskText())
                    .taskCompleted(taskEdit.isTaskCompleted())
                    .build();

            taskEditsDto.add(taskEditDto);
        }

            return taskEditsDto;
        }

        public List<TaskOrderDto> loadTaskOrder(long userId) {
            List<TaskOrder> taskOrders =  taskOrderRepository.findAllByUserId(userId);
            List<TaskOrderDto> taskOrderDtos = new ArrayList<>();

            for (TaskOrder taskOrder : taskOrders) {
            TaskOrderDto taskOrderDto = TaskOrderDto.builder()
                    .uuid(taskOrder.getTaskUuid())        
                    .sortOrder(taskOrder.getSortOrder())
                    .build();

            taskOrderDtos.add(taskOrderDto);
        }

            return taskOrderDtos;
        }
}
