package com.jucalc.spring_login.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jucalc.spring_login.dto.AuthResponse;
import com.jucalc.spring_login.dto.SignInRequest;
import com.jucalc.spring_login.dto.SignUpRequest;
import com.jucalc.spring_login.exception.ErrorResponse;
import com.jucalc.spring_login.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
@Validated
@Tag(name = "Authentication", description = "Controller that manages the authentication")
public class AuthenticationController {
  @Autowired
  private AuthService authService;

  @PostMapping("/signup")
  @Operation(summary = "Register a new user")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Register a username and return a token", 
                  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))}),
    @ApiResponse(responseCode = "400", description = "User already registred exist / Missing fields ", 
                  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}  )
  })
  public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignUpRequest request) {
      return ResponseEntity.ok(authService.signup(request));
  }

  @PostMapping(path = "/signin", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Login for a existing user")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Generate a token for an authenticated user", content = {@Content(schema = @Schema(implementation = SignInRequest.class))}),
    @ApiResponse(responseCode = "400", description = "Missing fields", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
    @ApiResponse(responseCode = "401", description = "Username or Password incorrect", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
  })
  public ResponseEntity<AuthResponse> signin(@Valid @RequestBody SignInRequest request) {
    return ResponseEntity.ok(authService.signin(request));
  }
  
}
