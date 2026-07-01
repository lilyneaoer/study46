package com.snow.study46.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.snow.study46.model.vo.BaseVo;
import com.snow.study46.model.vo.UserVo;
import com.snow.study46.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  UserService userService;

  @GetMapping("/getUser")
  public BaseVo<List<UserVo>> getUser(int id, String username, String password) {
    List<UserVo> userVoList = userService.getUser(id, username, password);
    String url = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
    return BaseVo.success(url, userVoList);
  }

  // 自定义SQL查询
  @GetMapping("/searchUser")
  public BaseVo<List<UserVo>> searchUser(int id, String username, String password) {
    List<UserVo> userVoList = userService.searchUser(id, username, password);
    String url = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
    return BaseVo.success(url, userVoList);
  }

  @GetMapping("/getUserById")
  public BaseVo<UserVo> getUserById(@RequestParam int id) {
    UserVo userVo = userService.getUserById(id);
    String url = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
    return BaseVo.success(url, userVo);
  }

  @GetMapping("/getUserByUsername")
  public BaseVo<List<UserVo>> getUserByUsername(@RequestParam String username) {
    List<UserVo> userVoList = userService.getUserByUsername(username);
    String url = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
    return BaseVo.success(url, userVoList);
  }

}
