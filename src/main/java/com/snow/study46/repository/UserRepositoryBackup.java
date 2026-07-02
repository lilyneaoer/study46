package com.snow.study46.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.snow.study46.entity.User;
import com.snow.study46.model.dto.RegisterDTO;

@Mapper
public interface UserRepositoryBackup {
  // 查询
  @Select("select * from user where username = #{username}")
  public Optional<User> searchUserByUsername(String username);

  @Select("select * from user where id = #{id}")
  public Optional<User> getUserById(String id);

  // 插入
  @Insert("insert into user(username, password) values(#{username}, #{password})")
  public int insertUser(RegisterDTO form);

  // 更新密码
  @Update("update user set password=#{password} where id=#{id}")
  public int updateUserPassword(String id, String password);

  // 删除用户
  @Delete("delete from user where id=#{id}")
  public int removeUserById(String id);
}
