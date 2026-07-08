package com.snow.study46.repository;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snow.study46.model.entity.UserBalance;

@Mapper
public interface UserBalanceRepository extends BaseMapper<UserBalance> {
}
