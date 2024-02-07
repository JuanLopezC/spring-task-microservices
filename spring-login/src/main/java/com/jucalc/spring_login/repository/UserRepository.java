package com.jucalc.spring_login.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jucalc.spring_login.entities.User;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByEmail(String email);
}