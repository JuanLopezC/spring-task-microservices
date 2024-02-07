package com.jucalc.spring_login.services;

import com.jucalc.spring_login.dto.AuthResponse;
import com.jucalc.spring_login.dto.SignInRequest;
import com.jucalc.spring_login.dto.SignUpRequest;

public interface AuthService {
  AuthResponse signup(SignUpRequest request);
  AuthResponse signin(SignInRequest request);

}
