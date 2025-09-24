package com.todoapp.todo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.todoapp.todo.api.dto.TaskDto;
import com.todoapp.todo.api.dto.TaskEditDto;
import com.todoapp.todo.api.dto.TaskOrderDto;
import com.todoapp.todo.enums.rowUpdateStatus;
import com.todoapp.todo.persistence.entity.Task;
import com.todoapp.todo.persistence.entity.TaskEdit;
import com.todoapp.todo.persistence.entity.TaskOrder;
import com.todoapp.todo.persistence.repository.TaskRepository;
import com.todoapp.todo.persistence.repository.TaskEditRepository;
import com.todoapp.todo.persistence.repository.TaskOrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UnloadDataService {

    private final TaskRepository taskRepository;
    private final TaskEditRepository taskEditRepository;
    private final TaskOrderRepository taskOrderRepository;

    public rowUpdateStatus unloadTask(TaskDto taskDto, long userId) {
        // laver en task i db
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

        //laver en tilhørende taskSort i db 
        try {

            TaskOrder highestTaskOrder = taskOrderRepository.findTopByUserIdOrderBySortOrderDesc(userId);

            //NextSortOrder starter med værdi 1 som long
            long nextSortOrder = 1;

            if (highestTaskOrder != null) {
                nextSortOrder = highestTaskOrder.getSortOrder() + 1;
            }

            TaskOrder taskOrder = new TaskOrder();
            taskOrder.setTaskUuid(taskDto.getTaskUuid());
            taskOrder.setSortOrder(nextSortOrder);
            taskOrder.setUserId(userId);
            taskOrderRepository.save(taskOrder);
        } catch (Exception e) {
            return rowUpdateStatus.ERROR;
        }

        return rowUpdateStatus.SUCCESS;

    }

    public rowUpdateStatus unloadtaskEdit(TaskEditDto taskEditDto) {
    try {
        TaskEdit taskEdit = new TaskEdit();
        taskEdit.setEditUuid(taskEditDto.getTaskEditsUuid());
        taskEdit.setDateEdited(taskEditDto.getDateEdited());
        taskEdit.setTaskDeleted(taskEditDto.getTaskDeleted());
        taskEdit.setTaskUuid(taskEditDto.getTaskUuid());
        taskEdit.setTaskText(taskEditDto.getTaskText());
        taskEdit.setTaskCompleted(taskEditDto.isTaskCompleted());
        taskEditRepository.save(taskEdit);

    } catch (Exception e) {
        return rowUpdateStatus.ERROR;
    }

    return rowUpdateStatus.SUCCESS;
    }

    public rowUpdateStatus updateTask(TaskDto taskDto) {
        try {
        Task task = taskRepository.findByTaskUuid(taskDto.getTaskUuid());

            task.setTaskText(taskDto.getTaskText());
            task.setTaskCompleted(taskDto.getTaskCompleted());
            task.setTaskCreated(taskDto.getTaskCreated());
            task.setTaskDeleted(taskDto.getTaskDeleted());

            taskRepository.save(task);

        } catch (Exception e) {
            return rowUpdateStatus.ERROR;
        }

    return rowUpdateStatus.SUCCESS;
    }

    public rowUpdateStatus updateTaskOrders(long userId, List<TaskOrderDto> taskOrderDtos) {
    try {
        List<TaskOrder> taskOrders = taskOrderRepository.findAllByUserId(userId);
        for (TaskOrder entity : taskOrders) {
            // find matchende dto baseret på fx taskUuid
            for (TaskOrderDto dto : taskOrderDtos) {
                
                if (dto.getUuid().equals(entity.getTaskUuid())) {
                    entity.setSortOrder(dto.getSortOrder());
                }
            }
        }

        taskOrderRepository.saveAll(taskOrders);

    } catch (Exception e) {
        return rowUpdateStatus.ERROR;
    }

    return rowUpdateStatus.SUCCESS;

    }

    public rowUpdateStatus deleteTasks(List<TaskDto> taskDtos) { 
        System.out.println(taskDtos);
    try {
            for (TaskDto taskDto: taskDtos) {
                Task task = taskRepository.findByTaskUuid(taskDto.getTaskUuid());
            if (task != null) {
                taskRepository.delete(task);
            }
        }
    } catch (Exception e) {
        return rowUpdateStatus.ERROR;
    }
    return rowUpdateStatus.SUCCESS;
    }

}
