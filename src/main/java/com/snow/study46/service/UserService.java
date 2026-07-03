package com.snow.study46.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snow.study46.entity.User;
import com.snow.study46.model.dto.ModifyPasswordDTO;
import com.snow.study46.model.dto.RegisterDTO;
import com.snow.study46.model.dto.UserIdDTO;
import com.snow.study46.model.vo.BaseVo;
import com.snow.study46.model.vo.UserVo;
import com.snow.study46.repository.UserRepository;
import com.snow.study46.utils.JwtUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  JwtUtils jwtUtils;

  public List<UserVo> getUserList() {
    List<User> userList = userRepository.selectList(null);
    List<UserVo> userVoList = new ArrayList<UserVo>();
    userList.forEach((User user) -> {
      UserVo userVo = new UserVo();
      userVo.setId(user.getId());
      userVo.setUsername(user.getUsername());
      userVoList.add(userVo);
    });
    return userVoList;
  }

  public BaseVo<Object> registerUser(User form) {
    String username = form.getUsername();
    QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
    // queryWrapper.like("username", username);
    queryWrapper.eq("username", username);
    User user = userRepository.selectOne(queryWrapper);
    if (user == null) {
      int result = userRepository.insert(form);
      if (result == 1) {
        return BaseVo.success("注册成功");
      }
      return BaseVo.fail("注册失败");
    } else {
      return BaseVo.fail("用户已存在");
    }
  }

  public BaseVo<Optional<UserVo>> login(RegisterDTO form) {
    String username = form.getUsername();
    String password = form.getPassword();
    QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
    queryWrapper.eq("username", username).eq("password", password); // 链式调用, where username=xxxx and password=xxxx
    User user = userRepository.selectOne(queryWrapper);
    if (user == null) {
      // return BaseVo.fail(null, "登录失败, 用户不存在");
      return BaseVo.fail(null, "登录失败, 用户名或密码错误");
    }
    /* if (!user.getPassword().equals(form.getPassword())) {
      return BaseVo.fail(null, "登录失败, 密码错误");
    } */
    UserVo userVo = new UserVo();
    userVo.setId(user.getId());
    userVo.setUsername(user.getUsername());
    userVo.setToken(jwtUtils.getToken());
    return BaseVo.success(Optional.of(userVo), "登录成功");
  }

  public BaseVo<Object> modifyUserPassword(ModifyPasswordDTO form) {
    User user = userRepository.selectById(form.getId());
    if (user == null) {
      return BaseVo.fail("用户不存在");
    } else {
      if (!user.getPassword().equals(form.getOldPassword())) {
        return BaseVo.fail("密码输入错误");
      }
      user.setPassword(form.getNewPassword());
      int result = userRepository.updateById(user);
      if (result == 1) {
        return BaseVo.success("用户" + user.getUsername() + "密码修改成功");
      }
    }
    return BaseVo.fail("修改失败");
  }

  public BaseVo<Object> removeUserById(UserIdDTO id) {
    QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
    queryWrapper.eq("id", id.getId());
    User user = userRepository.selectOne(queryWrapper);
    if (user == null) {
      return BaseVo.fail(null, "删除失败, id为" + id.getId() + "的用户不存在");
    }
    int result = userRepository.deleteById(user.getId());
    if (result == 1) {
      return BaseVo.success("用户" + user.getUsername() + "删除成功");
    }
    return BaseVo.fail("删除失败");
  }

  

/*   // 登录
  public BaseVo<Optional<UserVo>> login(RegisterDTO form) {
    Optional<User> opUser = userRepository.searchUserByUsername(form.getUsername());
    if (opUser.isEmpty()) {
      return BaseVo.fail(null, "登录失败, 用户不存在");
    }
    User user = opUser.get();
    if (!user.getPassword().equals(form.getPassword())) {
      return BaseVo.fail(null, "登录失败, 密码错误");
    }
    UserVo userVo = new UserVo();
    userVo.setId(user.getId());
    userVo.setUsername(user.getUsername());
    return BaseVo.success(Optional.of(userVo), "登录成功");
  }

  // 注册
  public BaseVo<Object> registerUser(RegisterDTO form) {
    Optional<User> opUser = userRepository.searchUserByUsername(form.getUsername());
    if (opUser.isPresent()) {
      return BaseVo.fail("用户已存在");
    }
    int result = userRepository.insertUser(form);
    if (result == 1) {
      return BaseVo.success("注册成功");
    }
    return BaseVo.fail("注册失败");
  }

  // 修改密码
  public BaseVo<Object> modifyUserPassword(ModifyPasswordDTO form) {
    String id = form.getId();
    Optional<User> opUser = userRepository.getUserById(id);
    if (opUser.isEmpty()) {
      return BaseVo.fail("用户不存在");
    }
    User user = opUser.get();
    if (!user.getPassword().equals(form.getOldPassword())) {
      return BaseVo.fail("密码输入错误");
    }
    int result = userRepository.updateUserPassword(id, form.getNewPassword());
    if (result == 1) {
      return BaseVo.success("修改成功");
    }
    return BaseVo.fail("修改失败");
  }

  // 删除用户
  public BaseVo<Object> removeUser(RegisterDTO form) {
    Optional<User> opUser = userRepository.searchUserByUsername(form.getUsername());
    if (opUser.isEmpty()) {
      return BaseVo.fail(null, "删除失败, 用户不存在");
    }
    User user = opUser.get();
    if (!user.getPassword().equals(form.getPassword())) {
      return BaseVo.fail(null, "删除失败, 密码错误");
    }
    int result = userRepository.removeUserById(user.getId());
    if (result == 1) {
      return BaseVo.success("用户" + user.getUsername() + "删除成功");
    }
    return BaseVo.fail("删除失败");
  }

  public BaseVo<Object> removeUserById(UserIdDTO id) {
    Optional<User> opUser = userRepository.getUserById(id.getId());
    if (opUser.isEmpty()) {
      return BaseVo.fail(null, "删除失败, id为" + id.getId() + "的用户不存在");
    }
    User user = opUser.get();
    int result = userRepository.removeUserById(user.getId());
    if (result == 1) {
      return BaseVo.success("用户" + user.getUsername() + "删除成功");
    }
    return BaseVo.fail("删除失败");
  } */
}
