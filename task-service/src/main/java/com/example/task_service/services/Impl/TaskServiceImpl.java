package com.example.task_service.services.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.task_service.dto.TaskRequest;
import com.example.task_service.dto.TaskResponse;
import com.example.task_service.entities.Task;
import com.example.task_service.repository.TaskRepository;
import com.example.task_service.services.TaskService;
import com.example.task_service.utils.JwtUtil;


@Service
public class TaskServiceImpl implements TaskService{

  @Autowired
  private TaskRepository taskRepository;
  @Autowired
  private JwtUtil jwtUtil;

  @Override
  public TaskResponse createTask(TaskRequest taskRequest) {
    Task newTasks = mapRequestToTask(taskRequest);
    newTasks.setUserId(jwtUtil.extractId());
    taskRepository.save(newTasks);
    return mapTaskToResponse(newTasks);
  }

  @Override
  public List<TaskResponse> listTasks() {
    return taskRepository.findByUserId(jwtUtil.extractId())
      .stream()
      .map(this::mapTaskToResponse).toList();
  }

  @Override
  public TaskResponse taskDetail(String id) {
    var task = taskRepository.findByIdAndUserId(id, jwtUtil.extractId())
      .orElseThrow(() -> new IllegalArgumentException("Task not found"));
    return mapTaskToResponse(task);
  }

  @Override
  public TaskResponse updateTask(String id, TaskRequest taskRequest) {
    taskRepository.findByIdAndUserId(id, jwtUtil.extractId())
      .orElseThrow(() -> new IllegalArgumentException("Task not found"));

    var updatedTask = mapRequestToTask(taskRequest);
    updatedTask.setId(id);
    updatedTask.setUserId(jwtUtil.extractId());
    taskRepository.save(updatedTask);
    return mapTaskToResponse(updatedTask);
  }

  @Override
  public void deleteTask(String id) {
    taskRepository.findByIdAndUserId(id, jwtUtil.extractId())
      .orElseThrow(() -> new IllegalArgumentException("Task not found"));
    taskRepository.deleteById(id);
  }

  private TaskResponse mapTaskToResponse(Task task) {
    return TaskResponse.builder()
      .id(task.getId())
      .title(task.getTitle())
      .description(task.getDescription())
      .isDone(task.isDone())
      .build();
  }

  private Task mapRequestToTask(TaskRequest taskRequest) {
    return Task.builder()
      .title(taskRequest.getTitle())
      .description(taskRequest.getDescription())
      .isDone(taskRequest.isDone())
      .build();
  }
  
}
