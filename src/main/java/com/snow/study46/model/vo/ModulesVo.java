package com.snow.study46.model.vo;

import java.util.List;

import lombok.Data;

@Data
public class ModulesVo {
  private int moduleId;
  private String moduleName;
  private List<PageDetailVo> page;
}
