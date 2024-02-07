package com.example.task_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.task_service.entities.Task;
import java.util.List;
import java.util.Optional;


public interface TaskRepository extends MongoRepository<Task, String> {
  List<Task> findByUserId(String userId);
  Optional<Task> findByIdAndUserId(String Id, String userId);
}
