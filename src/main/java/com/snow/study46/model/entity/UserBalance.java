package com.snow.study46.model.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("user_balance")
public class UserBalance {
  @TableId
  private String userId;
  private BigDecimal balance;
  private String comment;
}
