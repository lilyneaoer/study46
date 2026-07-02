package com.snow.study46.service;

import com.snow.study46.entity.User;
import com.snow.study46.model.dto.*;
import com.snow.study46.model.vo.BaseVo;
import com.snow.study46.model.vo.UserVo;
import com.snow.study46.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  UserRepository userRepository;

  public BaseVo<Optional<UserVo>> login(RegisterDTO form) {
    Optional<User> opUser = userRepository.searchUserByUsername(form.getUsername());
    if(!opUser.isEmpty()) {
      User user = opUser.get();
      if(user.getPassword().equals(form.getPassword())) {
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setUsername(user.getUsername());
        return BaseVo.success(Optional.of(userVo), "登录成功");
      } else {
        return BaseVo.fail(null, "登录失败, 密码错误");
      }
    } else {
      return BaseVo.fail(null, "登录失败, 用户不存在");
    }
  }
  
  public BaseVo<Object> registerUser(RegisterDTO form) {
    Optional<User> opUser = userRepository.searchUserByUsername(form.getUsername());
    if(opUser.isPresent()) {
      return BaseVo.fail("用户已存在");
    } else {
      int result = userRepository.insertUser(form);
      if (result == 1) {
        return BaseVo.success( "注册成功");
      } else {
        return BaseVo.fail("注册失败");
      }
    }
  }

  public BaseVo<Object> modifyUserPassword(ModifyPasswordDTO form) {
    String id = form.getId();
    Optional<User> opUser = userRepository.getUserById(id);
    if (!opUser.isEmpty()) {
      User user = opUser.get();
      if (user.getPassword().equals(form.getOldPassword())) {
        int result = userRepository.updateUserPassword(id, form.getNewPassword());
        if (result == 1) {
          return BaseVo.success("修改成功");
        } else {
          return BaseVo.fail("修改失败");
        }
      } else {
        return BaseVo.fail("密码输入错误");
      }
    } else {
      return BaseVo.fail("用户不存在");
    }
  }
}
