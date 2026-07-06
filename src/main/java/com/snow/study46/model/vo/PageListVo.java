package com.snow.study46.model.vo;

import java.util.List;

import lombok.Data;

@Data
public class PageListVo<T> {
  private int total;
  private List<T> list;
  private int pageNum;
  private int pageSize;
  private boolean hasMore;

  private PageListVo(int total, List<T> list, int pageNum, int pageSize, boolean hasMore) {
    this.total = total;
    this.list = list;
    this.pageNum = pageNum;
    this.pageSize = pageSize;
    this.hasMore = hasMore;
  }

  public static <F> PageListVo<F> getPageVo(int total, List<F> list, int pageNum, int pageSize, boolean hasMore) {
    return new PageListVo<>(total, list, pageNum, pageSize, hasMore);
  }
}
