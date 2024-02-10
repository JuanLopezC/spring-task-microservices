package com.jucalc.spring_login.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.lang.Arrays;

@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler{
  
  @ExceptionHandler(value = {BadCredentialsException.class})
  protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException exception, WebRequest request){

    ErrorResponse error = ErrorResponse.builder()
      .timestamp(LocalDateTime.now())
      .message(exception.getMessage())
      .errors(Arrays.asList(null))
      .build();

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);  
  }

}
