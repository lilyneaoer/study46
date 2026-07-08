package com.snow.study46.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.snow.study46.model.dto.CreateOrderDTO;
import com.snow.study46.model.entity.Orders;
import com.snow.study46.model.entity.User;
import com.snow.study46.model.entity.UserBalance;
import com.snow.study46.model.vo.BaseVo;
import com.snow.study46.model.vo.OrderListVo;
import com.snow.study46.model.vo.OrderVo;
import com.snow.study46.repository.OrderRepository;
import com.snow.study46.repository.UserBalanceRepository;
import com.snow.study46.repository.UserRepository;
import com.snow.study46.utils.Log;

@Service
public class OrderService {
  @Autowired
  OrderRepository orderRepository;
  @Autowired
  UserBalanceRepository userBalanceRepository;
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
    return BaseVo.fail(null, "购买失败");
  }

  /**
   * 修改订单状态
   * 
   * @param orderId
   * @param isDone
   * @return BaseVo
   */
  @Transactional
  public BaseVo<Object> changeStatus(String orderId, boolean isDone) {
    Orders order = orderRepository.selectById(orderId);
    if (order == null) {
      throw new RuntimeException("无此订单: " + orderId);
    }
    order.setDoneTime(new Date());
    order.setStatus(isDone ? "FINISH" : "NOT_PAY");
    OrderVo orderVo = createOrderVo(order);
    int result = orderRepository.updateById(order);
    if (result != 1) {
      throw new RuntimeException("订单状态修改失败");
    }
    BigDecimal amount = order.getAmount();
    UserBalance saleUserBalance = userBalanceRepository.selectById(order.getSaleId());
    UserBalance buyUserBalance = userBalanceRepository.selectById(order.getBuyId());
    if (saleUserBalance == null || buyUserBalance == null) {
      throw new RuntimeException("用户不存在");
    }
    if (isDone) {
      saleUserBalance.setBalance(saleUserBalance.getBalance().add(amount));
      buyUserBalance.setBalance(buyUserBalance.getBalance().subtract(amount));
    } else {
      saleUserBalance.setBalance(saleUserBalance.getBalance().subtract(amount));
      buyUserBalance.setBalance(buyUserBalance.getBalance().add(amount));
    }
    int saleResult = userBalanceRepository.updateById(saleUserBalance);
    int buyResult = userBalanceRepository.updateById(buyUserBalance);
    if (saleResult != 1 || buyResult != 1) {
      throw new RuntimeException(saleResult != 1 ? "收款失败" : "付款失败");
    }
    return BaseVo.success(orderVo, "订单更新成功");
  }

  // 删除
  public BaseVo<Object> remove(String id) {
    Orders order = orderRepository.selectById(id);
    if (order != null) {
      int result = orderRepository.deleteById(id);
      if (result == 1) {
        return BaseVo.success("订单" + id + "删除成功");
      } else {
        return BaseVo.fail("订单" + order.getOrderId() + "删除失败");
      }
    }
    return BaseVo.fail("无此订单");
  }

  private OrderVo createOrderVo(Orders order) {
    User saleUser = userRepository.selectById(order.getSaleId());
    User buyUser = userRepository.selectById(order.getBuyId());
    OrderVo orderVo = new OrderVo(saleUser, buyUser);
    orderVo.setOrderId(order.getOrderId());
    orderVo.setAmount(order.getAmount());
    orderVo.setCreateTime(order.getCreateTime());
    orderVo.setDoneTime(order.getDoneTime());
    orderVo.setStatus(order.getStatus());
    return orderVo;
  }
}
