package com.snow.study46.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snow.study46.model.dto.CreateOrderDTO;
import com.snow.study46.model.entity.Orders;
import com.snow.study46.model.entity.User;
import com.snow.study46.model.vo.BaseVo;
import com.snow.study46.model.vo.OrderListVo;
import com.snow.study46.model.vo.OrderVo;
import com.snow.study46.model.vo.PageInfoVo;
import com.snow.study46.repository.OrderRepository;
import com.snow.study46.repository.UserRepository;
import com.snow.study46.utils.Log;

@Service
public class OrderService {
  @Autowired
  OrderRepository orderRepository;
  @Autowired
  UserRepository userRepository;

  // 列表
  public BaseVo<OrderListVo> getList() {
    OrderListVo listVo = new OrderListVo();
    List<OrderVo> listOrderVo = new ArrayList<>();
    List<Orders> listOrder = orderRepository.selectList(null);
    listOrder.forEach((orderItem) -> {
      OrderVo orderVo = createOrderVo(orderItem);
      listOrderVo.add(orderVo);
    });
    listVo.setList(listOrderVo);
    return BaseVo.success(listVo, "查询成功");
  }

  // 创建
  public BaseVo<Object> createOrder(CreateOrderDTO createOrderDTO) {
    String buyId = createOrderDTO.getBuyId();
    String saleId = createOrderDTO.getSaleId();
    String originStr = System.currentTimeMillis() + buyId + saleId;
    String orderId = DigestUtils.md5DigestAsHex(originStr.getBytes());
    Orders order = new Orders();
    order.setOrderId(orderId);
    order.setBuyId(buyId);
    order.setSaleId(saleId);
    order.setAmount(createOrderDTO.getAmount());
    order.setStatus("NOT_PAY");
    int result = orderRepository.insert(order);
    Log.info("result" + result);
    if (result == 1) {
      order = orderRepository.selectById(orderId);
      OrderVo orderVo = createOrderVo(order);
      return BaseVo.success(orderVo, "购买成功");
    }
    return BaseVo.success(null, "购买失败");
  }

  // 修改状态
  public BaseVo<Object> changeStatus(String orderId, boolean isDone) {
    Orders order = orderRepository.selectById(orderId);
    if (order != null) {
      order.setStatus(isDone ? "PAY" : "NOT_PAY");
      int result = orderRepository.updateById(order);
      if (result == 1) {
        OrderVo orderVo = createOrderVo(orderRepository.selectById(orderId));
        return BaseVo.success(orderVo, "订单状态修改成功");
      } else {
        return BaseVo.fail(null, "订单状态修改失败");
      }
    }
    return BaseVo.fail(null, "无此用户");
  }

  private OrderVo createOrderVo(Orders order) {
    User saleUser = userRepository.selectById(order.getSaleId());
    User buyUser = userRepository.selectById(order.getBuyId());
    OrderVo orderVo = new OrderVo(saleUser, buyUser);
    orderVo.setOrderId(order.getOrderId());
    orderVo.setAmount(order.getAmount());
    orderVo.setCreateTime(order.getCreateTime());
    orderVo.setStatus(order.getStatus());
    return orderVo;
  }
}
