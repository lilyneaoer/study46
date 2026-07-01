package com.snow.study46.model.vo;

import lombok.Data;

@Data
// 返回前端的数据结构
public class BaseVo<T> {
  private boolean success;
  private String msg;
  private T data; // 泛型

  private BaseVo(boolean success, String url, String msg, T data) {
    this.success = success;
    this.msg = msg;
    this.data = data;
    System.out.println("======================request=================");
    System.out.println("time: " + new java.util.Date());
    System.out.println("url: " + url);
    System.out.println("data: " + data);
  }

  public static <F> BaseVo<F> success(String url, F data) {
    BaseVo<F> baseVo = new BaseVo<>(true, url, "成功", data);
    return baseVo;
  }

  public static <F> BaseVo<F> fail(String url, F data) {
    BaseVo<F> baseVo = new BaseVo<>(false, url, "失败", data);
    return baseVo;
  }
}
