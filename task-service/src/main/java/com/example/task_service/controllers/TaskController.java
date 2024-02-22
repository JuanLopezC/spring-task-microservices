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
import com.example.task_service.exception.ErrorResponse;
import com.example.task_service.services.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1")
@Tag(name = "Task Controller", description = "All Task functionality")
public class TaskController {

  @Autowired
  TaskService taskService;

  @GetMapping(path = "/tasks", produces = "application/json")
  @Operation(summary = "Get all task for the authenticated user")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "List of tasks", content = { 
      @Content(array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class))) }),
    @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content)
  })
  public ResponseEntity<List<TaskResponse>> getTasks() {
    return ResponseEntity.ok(taskService.listTasks());
  }

  @PostMapping(path = "/tasks", produces = "application/json", consumes = "application/json")
  @Operation(summary = "Create a new task")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Task created", content = @Content(schema = @Schema(implementation = TaskResponse.class))),
    @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content)
  })
  public ResponseEntity<TaskResponse> postMethodName(@RequestBody TaskRequest taskRequest) {
    return new ResponseEntity<TaskResponse>(taskService.createTask(taskRequest), HttpStatus.CREATED);

  }
  
  @GetMapping(path = "/tasks/{id}", produces = "application/json")
  @Operation(summary = "Get a task by id")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Task found", content = @Content(schema = @Schema(implementation = TaskResponse.class))),
    @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content),
    @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<TaskResponse> getTask(@PathVariable String id) {
    return ResponseEntity.ok(taskService.taskDetail(id));
  }

  @PutMapping("/tasks/{id}")
  @Operation(summary = "Update a task by id")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Task updated", content = @Content(schema = @Schema(implementation = TaskResponse.class))),
    @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content),
    @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<TaskResponse> updateTask(@PathVariable String id, @RequestBody TaskRequest taskRequest) {
    return ResponseEntity.ok(taskService.updateTask(id, taskRequest));
  }

  @DeleteMapping("/tasks/{id}")
  @Operation(summary = "Delete a task by id")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Task deleted", content = @Content),
    @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content),
    @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<String> deleteTask(@PathVariable String id) {
    taskService.deleteTask(id);
    return ResponseEntity.ok("Task deleted");
  } 
  
}
