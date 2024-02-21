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
public class SignUpRequest {
  @Valid
  @NotBlank(message = "Email required")
  private String email;
  @Valid
  @NotBlank(message = "Pasword required")
  private String password;
  @Valid
  @NotBlank(message = "First name required")
  private String firstName;
  @Valid
  @NotBlank(message = "Last Name required")
  private String lastName;

}
