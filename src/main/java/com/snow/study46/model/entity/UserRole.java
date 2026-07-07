package com.snow.study46.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("user_role")
public class UserRole {
  @TableId(type = IdType.AUTO)
  private int userId;
  private int roleId;
  private String comment;
}
