package com.snow.study46.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("role_page")
public class RolePage {
  @TableId(type = IdType.AUTO)
  private int id;
  private int role_id;
  private int page_id;
}
