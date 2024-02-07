package com.example.task_service.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document("Task")
@Getter
@Setter
@Builder
public class Task {
  @Id
  private String id;
  private String title;
  private String description;
  @Builder.Default
  private boolean isDone = false;
  private String userId;
}
