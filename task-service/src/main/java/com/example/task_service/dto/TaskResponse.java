package com.example.task_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TaskResponse {
  private String id;
  private String title;
  private String description;
  private boolean isDone;
}
