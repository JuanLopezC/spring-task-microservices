package com.jucalc.spring_login.services;


import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;

import com.jucalc.spring_login.entities.User;


public interface JwtService {
  
  String extractUsername(String token);

  String generateToken(User user);

  boolean isTokenValid(String token, UserDetails user);

  SecretKey getSigningKey();
}
