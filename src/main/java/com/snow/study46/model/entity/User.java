package com.snow.study46.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("user") // 表名
public class User {
  @TableId(type = IdType.AUTO) // 主键
  private String id;
  private String username;
  private String password;
  @TableField(fill = FieldFill.DEFAULT) // 默认值
  private String createTime;
  @TableField(fill = FieldFill.DEFAULT) // 默认值
  private String updateTime;
}
