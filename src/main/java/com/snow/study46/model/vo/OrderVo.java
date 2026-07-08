package com.snow.study46.model.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.snow.study46.model.entity.User;

import lombok.Data;

@Data
public class OrderVo {
  private String orderId;
  private Buy buy;
  private Sale sale;
  private BigDecimal amount;
  private String status;
  private Date createTime;
  private Date doneTime;

  public OrderVo(User sale, User buy) {
    Sale saleVo = new Sale();
    saleVo.setId(sale.getId());
    saleVo.setUsername(sale.getUsername());
    this.sale = saleVo;

    Buy buyVo = new Buy();
    buyVo.setId(buy.getId());
    buyVo.setUsername(buy.getUsername());
    this.buy = buyVo;
  }

  @Data
  public static class Buy {
    private String id;
    private String username;
  }

  @Data
  public static class Sale {
    private String id;
    private String username;
  }
}
