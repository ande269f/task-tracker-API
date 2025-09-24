package com.todoapp.todo.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.todoapp.todo.api.dto.TaskDto;
import com.todoapp.todo.api.dto.TaskEditDto;
import com.todoapp.todo.api.dto.TaskOrderDto;
import com.todoapp.todo.api.dto.UserTaskDataDto;
import com.todoapp.todo.enums.rowUpdateStatus;
import com.todoapp.todo.services.LoadDataService;
import com.todoapp.todo.services.UnloadDataService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

@RequiredArgsConstructor
@RestController
@RequestMapping("/data")
public class TaskController {

    private final UnloadDataService unloadDataService;
    private final LoadDataService loadDataService;

    @PostMapping("/unloadTask/{userId}")
    public ResponseEntity<String> unloadTasks(@RequestBody TaskDto taskDto, @PathVariable long userId) {
        rowUpdateStatus status = unloadDataService.unloadTask(taskDto, userId);
        return ResponseEntity.ok(status.toString());
    }

    @GetMapping("/loadTasks/")
    public ResponseEntity<?> loadTasks(Authentication authentication) {
        long userId = loadDataService.fetchUserId(authentication);
        List<TaskDto> tasksDto = loadDataService.loadTasks(userId);

        return ResponseEntity.ok(tasksDto);
    }

    @PostMapping("/unloadTaskEdit/")
    public ResponseEntity<String> unloadTaskEdit(@RequestBody TaskEditDto taskEditDto) {
        rowUpdateStatus status = unloadDataService.unloadtaskEdit(taskEditDto);

        return ResponseEntity.ok(status.toString());
    }

    @PostMapping("/updateTaskOrder/")
    public ResponseEntity<?> updateTaskOrder(Authentication authentication, @RequestBody List<TaskOrderDto> TaskOrdersDto) {
        long userId = loadDataService.fetchUserId(authentication);
        rowUpdateStatus status = unloadDataService.updateTaskOrders(userId, TaskOrdersDto);

        return ResponseEntity.ok(status.toString());
    }

    @PostMapping("/updateTask/")
    public ResponseEntity<?> updateTask(Authentication authentication, @RequestBody TaskDto taskDto) {
        rowUpdateStatus status = unloadDataService.updateTask(taskDto);

        return ResponseEntity.ok(status.toString());
    }

    @DeleteMapping("/deleteTasks/")
    public ResponseEntity<String> deleteTasks(@RequestBody List<TaskDto> taskDto) {
        rowUpdateStatus status = unloadDataService.deleteTasks(taskDto);        
        
        return ResponseEntity.ok(status.toString());
    }






    @GetMapping("/loadTaskEdits/{taskUuid}")
    public ResponseEntity<?> loadTaskEdits(Authentication authentication, @PathVariable String taskUuid) {
        List<TaskEditDto> tasksEditsDto = loadDataService.loadTaskEdits(taskUuid);

        return ResponseEntity.ok(tasksEditsDto);
    }


    @GetMapping("/loadUserData/")
    public ResponseEntity<UserTaskDataDto> loadUserData(Authentication authentication) {
        long userId = loadDataService.fetchUserId(authentication);
        List<TaskDto> tasksDto = loadDataService.loadTasks(userId);
        List<TaskOrderDto> taskOrderDto = loadDataService.loadTaskOrder(userId);

        UserTaskDataDto userTaskDataDto = UserTaskDataDto.builder()
            .tasks(tasksDto)
            .sortTasks(taskOrderDto)
            .build();

        return ResponseEntity.ok(userTaskDataDto);
    }


    
    
}
