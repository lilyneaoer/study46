package com.snow.study46.model.vo;

import lombok.Data;

@Data
// 返回前端的数据结构
public class BaseVo<T> {
  private boolean success;
  private String msg;
  private T data; // 泛型

  private BaseVo(boolean success, String msg, T data) {
    this.success = success;
    this.msg = msg;
    this.data = data;
  }

  public static <F> BaseVo<F> success(String msg) {
    return new BaseVo<>(true, msg, null);
  }

  public static <F> BaseVo<F> success(F data, String msg) {
    BaseVo<F> baseVo = new BaseVo<>(true, msg, data);
    return baseVo;
  }

  public static <F> BaseVo<F> fail(String msg) {
    return new BaseVo<>(false, msg, null);
  }

  public static <F> BaseVo<F> fail(F data, String msg) {
    BaseVo<F> baseVo = new BaseVo<>(false, msg, data);
    return baseVo;
  }
}
