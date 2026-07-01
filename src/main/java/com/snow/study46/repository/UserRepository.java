package com.snow.study46.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.snow.study46.entity.User;

@Mapper
public interface UserRepository {
  // SELECT * from user WHERE id=1;
  @Select("SELECT * from user WHERE id = #{id}")
  public User searchId(int id);

  // SELECT * from user WHERE username LIKE '%word%';
  @Select("SELECT * from user WHERE username LIKE CONCAT('%', #{username}, '%')")
  public List<User> searchUsername(String username);

  // SELECT * from user WHERE
  // id=1 AND username LIKE '%word%' AND password LIKE '%word%';
  @Select("SELECT * from user WHERE id = #{id} AND username LIKE CONCAT('%', #{username}, '%') AND password LIKE CONCAT('%', #{password}, '%')")
  public List<User> searchUser(int id, String username, String password);
}
