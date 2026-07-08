package com.snow.study46.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snow.study46.model.dto.ChangeOrderDTO;
import com.snow.study46.model.dto.CreateOrderDTO;
import com.snow.study46.model.dto.OrderIdDTO;
import com.snow.study46.model.vo.BaseVo;
import com.snow.study46.model.vo.OrderListVo;
import com.snow.study46.service.OrderService;

@RestController
@RequestMapping("order")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/create")
  // 创建订单
  public BaseVo<Object> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
    return orderService.createOrder(createOrderDTO);
  }

  // 修改状态
  @PostMapping("/changeStatus")
  public BaseVo<Object> changeStatus(@RequestBody ChangeOrderDTO changeOrderDTO) {
    try {
      return orderService.changeStatus(changeOrderDTO.getOrderId(), changeOrderDTO.isDone());
    } catch (RuntimeException err) {
      return BaseVo.fail(err.getMessage());
    }
  }

  // 删除
  @PostMapping("/remove")
  public BaseVo<Object> postMethodName(@RequestBody OrderIdDTO removeOrderDTO) {
    return orderService.remove(removeOrderDTO.getId());
  }

  // 列表
  @GetMapping("/list")
  public BaseVo<OrderListVo> getList() {
    return orderService.getList();
  }

}
