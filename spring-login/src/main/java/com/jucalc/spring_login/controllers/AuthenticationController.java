package com.jucalc.spring_login.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jucalc.spring_login.dto.AuthResponse;
import com.jucalc.spring_login.dto.SignInRequest;
import com.jucalc.spring_login.dto.SignUpRequest;
import com.jucalc.spring_login.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
  @Autowired
  private AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> signup(@RequestBody SignUpRequest request) {
      return ResponseEntity.ok(authService.signup(request));
  }

  @PostMapping("/signin")
  public ResponseEntity<AuthResponse> signin(@RequestBody SignInRequest request) {
    return ResponseEntity.ok(authService.signin(request));
  }
  
}
