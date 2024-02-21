package com.jucalc.spring_login.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {

  @Valid 
  @NotBlank(message = "Email field required")
  private String email;
  @Valid 
  @NotBlank(message = "Password field required")
  private String password;

}
