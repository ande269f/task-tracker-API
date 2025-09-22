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

    public rowUpdateStatus unloadTaskOrder(long userId, List<TaskOrderDto> taskOrderDtos) {
    try {
        for (TaskOrderDto taskOrderDto: taskOrderDtos) {
        TaskOrder taskOrder = new TaskOrder();
        taskOrder.setTaskUuid(taskOrderDto.getUuid());
        taskOrder.setSortOrder(taskOrderDto.getSortOrder());
        taskOrder.setId(userId);
        taskOrderRepository.save(taskOrder);
        }

    } catch (Exception e) {
        return rowUpdateStatus.ERROR;
    }

    return rowUpdateStatus.SUCCESS;

    }

}
