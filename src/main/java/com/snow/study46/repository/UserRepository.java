/*
 * @Author: maxuehao lilyneao@foxmail.com
 * @Date: 2026-07-03 19:48:04
 * @LastEditors: maxuehao lilyneao@foxmail.com
 * @LastEditTime: 2026-07-04 17:49:55
 * @Description: 文件概要说明
 */
package com.snow.study46.repository;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snow.study46.entity.User;

@Mapper
public interface UserRepository extends BaseMapper<User> {
  // myBatis
  // // 查询
  // @Select("select * from user where username = #{username}")
  // public Optional<User> searchUserByUsername(String username);

  // @Select("select * from user where id = #{id}")
  // public Optional<User> getUserById(String id);

  // // 插入
  // @Insert("insert into user(username, password) values(#{username},
  // #{password})")
  // public int insertUser(RegisterDTO form);

  // // 更新密码
  // @Update("update user set password=#{password} where id=#{id}")
  // public int updateUserPassword(String id, String password);

  // // 删除用户
  // @Delete("delete from user where id=#{id}")
  // public int removeUserById(String id);
}

