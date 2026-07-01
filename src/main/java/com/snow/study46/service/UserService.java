package com.snow.study46.service;

import com.snow.study46.entity.User;
import com.snow.study46.model.vo.UserVo;
import com.snow.study46.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  UserRepository userRepository;

  // 根所id, username, password 模糊查找
  public List<UserVo> getUser(int id, String username, String password) {
    List<User> userList = userRepository.findByIdAndUsernameContainingAndPasswordContaining(id, username, password);
    List<UserVo> userVoList = new ArrayList<UserVo>();
    userList.forEach((User user) -> {
      UserVo userVo = new UserVo();
      userVo.setId(user.getId());
      userVo.setUsername(user.getUsername());
      userVoList.add(userVo);
    });
    return userVoList;
  }

  // 根所id, username, password 模糊查找
  public List<UserVo> searchUser(int id, String username, String password) {
    List<User> userList = userRepository.searchUser(id, username, password);
    List<UserVo> userVoList = new ArrayList<UserVo>();
    userList.forEach((User user) -> {
      UserVo userVo = new UserVo();
      userVo.setId(user.getId());
      userVo.setUsername(user.getUsername());
      userVoList.add(userVo);
    });
    return userVoList;
  }

  public UserVo getUserById(int id) {
    User user = userRepository.findById(id);
    UserVo userVo = new UserVo();
    // 也可写为统一返回数组
    if (user != null) {
      userVo.setId(user.getId());
      userVo.setUsername(user.getUsername());
    } else {
      userVo.setId(0);
      userVo.setUsername(null);
    }
    return userVo;
  }

  public List<UserVo> getUserByUsername(String username) {
    List<User> userList = userRepository.findByUsernameContaining(username);
    List<UserVo> userVoList = new ArrayList<UserVo>();
    userList.forEach((User user) -> {
      UserVo userVo = new UserVo();
      userVo.setId(user.getId());
      userVo.setUsername(user.getUsername());
      userVoList.add(userVo);
    });
    return userVoList;
  }
}
