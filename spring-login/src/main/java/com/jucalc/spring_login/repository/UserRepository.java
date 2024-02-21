package com.jucalc.spring_login.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.jucalc.spring_login.entities.User;

public interface UserRepository extends MongoRepository<User, String> {
  @Query(value = "{'email': {$regex : ?0, $options: 'i'}}")
  Optional<User> findByEmail(String email);
}
