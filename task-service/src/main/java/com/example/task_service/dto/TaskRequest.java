package com.example.task_service.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaskRequest {
  private String title;
  private String description;
  private boolean isDone;
}
