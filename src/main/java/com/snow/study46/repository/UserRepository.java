package com.snow.study46.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snow.study46.model.dto.UserListDTO;
import com.snow.study46.model.entity.User;

@Mapper
public interface UserRepository extends BaseMapper<User> {
  // @Select("select * from user where id like concat('%', #{userListDTO.id}, '%')
  // and username like concat('%', #{userListDTO.username}, '%') and create_time >
  // #{userListDTO.startTime} and create_time < #{userListDTO.endTime} limit
  // #{userListDTO.pageSize} offset #{offset}")
  public List<User> getUserList(@Param("userListDTO") UserListDTO userListDTO, @Param("offset") int offset);

  // @Select("select count(*) from user")
  public int getCountUser(@Param("userListDTO") UserListDTO userListDTO);
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

