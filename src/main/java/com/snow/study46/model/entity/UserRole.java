package com.snow.study46.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("user_role")
public class UserRole {
  @TableId
  // 外键 → user.id (String UUID)
  private String userId;
  // 外键 → role.role_id (int)
  private int roleId;
  private String comment;
}
