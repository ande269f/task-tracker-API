package com.todoapp.todo.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "task_order")
public class TaskOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto increment fra DB
    private Long id;
    private String taskUuid;
    private long sortOrder;
    private long userId;
}