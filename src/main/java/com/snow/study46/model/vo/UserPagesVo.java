package com.snow.study46.model.vo;

import java.util.List;

import lombok.Data;

@Data
public class UserPagesVo<T> {
  private String id;
  private String username;
  private int roleId;
  private String roleName;
  private List<PageInfoVo> pages;
}
