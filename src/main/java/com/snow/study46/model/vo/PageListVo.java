package com.snow.study46.model.vo;

import java.util.List;

import lombok.Data;

/**
 * 分页列表 VO，用于返回分页查询结果。
 *
 * @param <T> 列表元素类型
 */
@Data
public class PageListVo<T> {
  /** 总记录数 */
  private int total;
  /** 当前页数据列表 */
  private List<T> list;
  /** 当前页码 */
  private int pageNum;
  /** 每页大小 */
  private int pageSize;
  /** 是否还有更多数据 */
  private boolean hasMore;

  /**
   * 私有构造方法，通过静态工厂方法创建实例。
   *
   * @param total    总记录数
   * @param list     当前页数据列表
   * @param pageNum  当前页码
   * @param pageSize 每页大小
   * @param hasMore  是否还有更多数据
   */
  private PageListVo(int total, List<T> list, int pageNum, int pageSize, boolean hasMore) {
    this.total = total;
    this.list = list;
    this.pageNum = pageNum;
    this.pageSize = pageSize;
    this.hasMore = hasMore;
  }

  /**
   * 创建分页 VO 实例的静态工厂方法。
   *
   * @param total    总记录数
   * @param list     当前页数据列表
   * @param pageNum  当前页码
   * @param pageSize 每页大小
   * @param hasMore  是否还有更多数据
   * @param <F>      列表元素类型
   * @return 分页列表 VO
   */
  public static <F> PageListVo<F> getPageVo(int total, List<F> list, int pageNum, int pageSize, boolean hasMore) {
    return new PageListVo<>(total, list, pageNum, pageSize, hasMore);
  }
}
