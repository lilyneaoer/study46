package com.snow.study46.model.vo;

import java.util.List;

import lombok.Data;

@Data
public class PageInfoVo {
  private int pageId;
  private String pageName;
  private String pagePath;
  private int parentId;
  private int moduleId;
  private String moduleName;
  private List<PageInfoVo> children;
}
