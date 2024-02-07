package com.example.task_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task_service.dto.TaskRequest;
import com.example.task_service.dto.TaskResponse;
import com.example.task_service.services.TaskService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1")
public class TaskController {

  @Autowired
  TaskService taskService;

  @GetMapping("/tasks")
  public ResponseEntity<List<TaskResponse>> getTasks() {
    return ResponseEntity.ok(taskService.listTasks());
  }

  @PostMapping("/tasks")
  public ResponseEntity<TaskResponse> postMethodName(@RequestBody TaskRequest taskRequest) {
    return new ResponseEntity<TaskResponse>(taskService.createTask(taskRequest), HttpStatus.CREATED);

  }
  
  @GetMapping("/tasks/{id}")
  public ResponseEntity<TaskResponse> getTask(@PathVariable String id) {
    return ResponseEntity.ok(taskService.taskDetail(id));
  }

  @PutMapping("/tasks/{id}")
  public ResponseEntity<TaskResponse> updateTask(@PathVariable String id, @RequestBody TaskRequest taskRequest) {
    return ResponseEntity.ok(taskService.updateTask(id, taskRequest));
  }

  @DeleteMapping("/tasks/{id}")
  public ResponseEntity<String> deleteTask(@PathVariable String id) {
    taskService.deleteTask(id);
    return ResponseEntity.ok("Task deleted");
  } 
  
}
