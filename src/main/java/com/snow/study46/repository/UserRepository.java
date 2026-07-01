package com.snow.study46.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.snow.study46.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  // jpa操作
  public User findById(int id);

  // public User findByUsername(String username); // 准确查找
  public List<User> findByUsernameContaining(String username); // 模糊查找

  // 根所id, username, password 模糊查找
  public List<User> findByIdAndUsernameContainingAndPasswordContaining(int id, String username, String password);

  // 自定义SQL查询
  // SELECT * from `user`
  // WHERE id=1 and username LIKE '%username%' LIKE password = '%password%';
  @Query("SELECT u from User u WHERE "
      + "u.id = :id AND "
      + "u.username LIKE CONCAT('%', :username, '%') AND "
      + "u.password LIKE CONCAT('%', :password, '%')")
  public List<User> searchUser(int id, String username, String password);
}
