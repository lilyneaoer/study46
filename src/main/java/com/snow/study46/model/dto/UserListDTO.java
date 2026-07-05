package com.snow.study46.model.dto;

import lombok.Data;

// 用户列表
@Data
public class UserListDTO {
  private int pageNum = 1;
  private int pageSize = 10;
  private String id;
  private String username;
  private String startTime;
  private String endTime;
}
