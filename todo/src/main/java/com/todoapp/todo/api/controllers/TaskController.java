package com.todoapp.todo.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.todoapp.todo.api.dto.TaskDto;
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
        List<TaskDto> tasksDto = loadDataService.loadTasks(authentication);
        System.out.println(tasksDto);
        return ResponseEntity.ok(tasksDto);
    }
    
    
}
