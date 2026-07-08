package com.snow.study46.model.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreateOrderDTO {
  private String buyId;
  private String SaleId;
  private BigDecimal amount;
}
