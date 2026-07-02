package com.snow.study46.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snow.study46.model.dto.*;
import com.snow.study46.model.vo.*;
import com.snow.study46.service.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  UserService userService;

  @PostMapping("/register")
  public BaseVo<Object> registerUser(@RequestBody RegisterDTO form) {
    return userService.registerUser(form);
  }

  @PostMapping("/login")
  public BaseVo<Optional<UserVo>> login(@RequestBody RegisterDTO form) {
    return userService.login(form);
  }
  
  @PostMapping("/modifyPassword")
  public BaseVo<Object> modifyPassword(@RequestBody ModifyPasswordDTO form) {
    return userService.modifyUserPassword(form);
  }
}
