package com.jucalc.spring_login.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jucalc.spring_login.dto.AuthResponse;
import com.jucalc.spring_login.dto.SignInRequest;
import com.jucalc.spring_login.dto.SignUpRequest;
import com.jucalc.spring_login.entities.User;
import com.jucalc.spring_login.repository.UserRepository;
import com.jucalc.spring_login.services.AuthService;
import com.jucalc.spring_login.services.JwtService;


@Service
public class AuthServiceImpl implements AuthService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JwtService jwtService;

  @Override
  public AuthResponse signup(SignUpRequest request) {
    var user = User.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .build();
    userRepository.save(user);
    var jwt = jwtService.generateToken(user);
    return AuthResponse.builder().token(jwt).build();
  }

  @Override
  public AuthResponse signin(SignInRequest request) {
    try {
    var user = userRepository.findByEmail(request.getEmail()).get();
    var jwt = jwtService.generateToken(user);
    return AuthResponse.builder().token(jwt).build();
    } catch (BadCredentialsException ex) {
      //This is for send custom exception message provided by the GlobalExceptionController
      throw new BadCredentialsException("Username or password incorrect.");
    } catch (Exception ex) {
      //Better change to log
      System.out.println(ex.getMessage());
      throw new BadCredentialsException("An error occurred while trying to authenticate");
    }
  }

}