package com.snow.study46.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("role")
public class Role {
  @TableId(type = IdType.AUTO)
  private int pageId;
  private String pageName;
  private String pagePath;
  private int parent_id;
  private int module_id;
}
