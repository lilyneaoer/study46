package com.snow.study46.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "user") // 如若不写, 默认name为类名为小写做为表象
// 实体类和表同名
public class User {
  @Id // 定义主键
  private int id;
  private String username;
  private String password;
}
