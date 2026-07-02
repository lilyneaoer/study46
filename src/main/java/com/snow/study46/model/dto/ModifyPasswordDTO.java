package com.snow.study46.model.dto;

import lombok.Data;

@Data
public class ModifyPasswordDTO {
  private String id;
  private String oldPassword;
  private String newPassword;
}
