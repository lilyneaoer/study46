package com.snow.study46.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("Order")
public class Order {
  @TableId
  private String orderId;
  private String buyId;
  private String SaleId;
  private BigDecimal amount;
  private String status;
  @TableField(fill = FieldFill.DEFAULT)
  private Date createTime;
  private Date doneTime;
}
