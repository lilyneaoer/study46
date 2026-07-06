package com.snow.study46.model.vo;

import java.util.List;

import lombok.Data;

@Data
public class PageDetailVo {
  private int pageId;
  private String pageName;
  private String pagePath;
  private List<PageDetailVo> children;
}
