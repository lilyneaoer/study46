package com.snow.study46.model.vo;

import java.util.List;

import lombok.Data;

@Data
public class PageVo<T> {
  private int total;
  private List<T> list;
  private int pageNum;
  private int pageSize;
  private boolean hasMore;

  private PageVo(int total, List<T> list, int pageNum, int pageSize, boolean hasMore) {
    this.total = total;
    this.list = list;
    this.pageNum = pageNum;
    this.pageSize = pageSize;
    this.hasMore = hasMore;
  }

  public static <F> PageVo<F> getPageVo(int total, List<F> list, int pageNum, int pageSize, boolean hasMore) {
    return new PageVo<>(total, list,  pageNum, pageSize, hasMore);
  }
}
