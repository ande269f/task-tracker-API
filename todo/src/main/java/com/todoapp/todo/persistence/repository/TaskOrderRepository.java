package com.todoapp.todo.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoapp.todo.persistence.entity.TaskOrder;

@Repository
public interface TaskOrderRepository extends JpaRepository<TaskOrder, Long> {
    List<TaskOrder> findAllByUserId(long userId);
    TaskOrder findByTaskUuid(String taskUuid);
    TaskOrder findTopByUserIdOrderBySortOrderDesc(long userId);
    
}
