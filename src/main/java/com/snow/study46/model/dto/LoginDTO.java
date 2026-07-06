package com.snow.study46.model.dto;

import lombok.Data;

@Data
public class LoginDTO {
  private String username;
  private String password;
  private String verifyCode;
}
