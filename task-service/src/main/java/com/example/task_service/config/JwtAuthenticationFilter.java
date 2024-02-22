package com.example.task_service.config;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.task_service.exception.ErrorResponse;
import com.example.task_service.utils.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.lang.Arrays;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtil jwtUtil;

  private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    try {
      if(!tokenExists(request, response)) {
        filterChain.doFilter(request, response);
        return;
      }

      final String token = request.getHeader(HEADER).replace(PREFIX, "");
      if(jwtUtil.isTokenExpired(token)) {
        return;
      }
      final String userName = jwtUtil.extractId();

      if(userName.isEmpty() && SecurityContextHolder.getContext().getAuthentication() != null) {
        filterChain.doFilter(request, response);
        return;
      }
      SecurityContext context = SecurityContextHolder.createEmptyContext();
      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName, null, null);
      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      context.setAuthentication(authToken);
      SecurityContextHolder.setContext(context);
      filterChain.doFilter(request, response);
    } 
    catch(ExpiredJwtException | MalformedJwtException | SignatureException ex) {
      System.out.println(ex.getMessage()); //Change for log message
      handleExceptionAuth(request, response, ex);
      return;
    } 
    catch(Exception ex) {
      System.out.println(ex.getMessage()); //Cahnge for log message
      handleExceptionAuth(request, response, ex);
      return;
    }
  }


  private boolean tokenExists(HttpServletRequest request, HttpServletResponse response) {
    String authHeader = request.getHeader(HEADER);
    if(authHeader == null || !authHeader.startsWith(PREFIX)) {
      return false;
    }
    return true;
  }

  private void handleExceptionAuth(HttpServletRequest request, HttpServletResponse response, Exception ex)
      throws JsonProcessingException, IOException {

    try {      
      ErrorResponse error = makeResponse(ex.getMessage());
      String json = new ObjectMapper().findAndRegisterModules().writeValueAsString(error);
      response.setContentType(MediaType.APPLICATION_JSON.toString());
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.getWriter().write(json);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private ErrorResponse makeResponse(String message) {
    return ErrorResponse.builder()
      .timestamp(LocalDateTime.now())
      .errors(Arrays.asList(null))
      .message(message)
      .build();
  }

}