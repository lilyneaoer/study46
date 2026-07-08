package com.snow.study46.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snow.study46.model.dto.CreateOrderDTO;
import com.snow.study46.repository.OrderRepository;

@Service
public class OrderService {
  @Autowired
  OrderRepository orderRepository;

  public Object createOrder(CreateOrderDTO createOrderDTO) {
    return null;
  }
}
