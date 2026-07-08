package com.snow.study46.repository;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snow.study46.model.entity.Order;

@Mapper
public interface OrderRepository extends BaseMapper<Order> {

}
