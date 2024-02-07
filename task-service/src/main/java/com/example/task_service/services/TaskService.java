package com.example.task_service.services;

import java.util.List;

import com.example.task_service.dto.TaskRequest;
import com.example.task_service.dto.TaskResponse;

public interface TaskService {

  public TaskResponse createTask(TaskRequest taskRequest);
  public List<TaskResponse> listTasks();
  public TaskResponse taskDetail(String id);
  public TaskResponse updateTask(String id, TaskRequest taskRequest);
  public void deleteTask(String id);
}
