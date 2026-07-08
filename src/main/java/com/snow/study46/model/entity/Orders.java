package com.snow.study46.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("orders")
public class Orders {
  @TableId
  private String orderId;
  private String buyId;
  private String saleId;
  private BigDecimal amount;
  private String status;
  @TableField(fill = FieldFill.DEFAULT)
  private Date createTime;
  @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
  private Date doneTime;
}
