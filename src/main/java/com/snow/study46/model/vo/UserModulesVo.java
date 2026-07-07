package com.snow.study46.model.vo;

import java.util.List;

import lombok.Data;

@Data
public class UserModulesVo {
  private int moduleId;
  private String moduleName;
  private List<PageInfoVo> pages;
}
