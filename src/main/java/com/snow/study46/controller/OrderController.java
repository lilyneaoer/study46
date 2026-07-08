package com.snow.study46.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("order")
public class OrderController {
  @PostMapping("/create")
  // 创建订单
  public Object createOrder(@RequestBody String entity) {
    return null;
  }

}
