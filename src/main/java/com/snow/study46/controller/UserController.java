/*
 * @Author: maxuehao lilyneao@foxmail.com
 * @Date: 2026-07-03 19:48:04
 * @LastEditors: maxuehao lilyneao@foxmail.com
 * @LastEditTime: 2026-07-04 20:46:32
 * @Description: 文件概要说明
 */
package com.snow.study46.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.snow.study46.entity.User;
import com.snow.study46.model.dto.*;
import com.snow.study46.model.vo.*;
import com.snow.study46.service.UserService;
import com.snow.study46.utils.Log;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.boot.jaxb.Origin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  UserService userService;

  @GetMapping("/search")
  public BaseVo<List<UserVo>> search(@RequestParam String keyword) {
    List<UserVo> userVoList = userService.search(keyword);
    return BaseVo.success(userVoList, "成功");
  }
  
  @GetMapping("/list")
  public BaseVo<List<UserVo>> getList() {
    List<UserVo> userVoList = userService.getUserList();
    return BaseVo.success(userVoList, "成功");
  }
  
  @PostMapping("/register")
  public BaseVo<Object> registerUser(@RequestBody User form) {
    return userService.registerUser(form);
  }

  @PostMapping("/login")
  public BaseVo<Optional<UserVoLogin>> login(@RequestBody RegisterDTO form, HttpSession session) {
    // session.getAttribute("code");
    return userService.login(form);
  }
  
  @PostMapping("/modifyPassword")
  public BaseVo<Object> modifyPassword(@RequestBody ModifyPasswordDTO form) {
    return userService.modifyUserPassword(form);
  }

  // @PostMapping("/remove")
  // public BaseVo<Object> remove(@RequestBody RegisterDTO form) {
  //   return userService.removeUser(form);
  // }
  
  @PostMapping("/removeUser")
  public BaseVo<Object> removeUser(@RequestBody UserIdDTO id) {
    return userService.removeUserById(id);
  }

  @GetMapping("/getCode")
  public String getCode(@RequestParam String param, HttpSession session) {
    session.setAttribute("code", "abcde");
      return "ok";
  }
  

  @GetMapping("/testCROS")
  public BaseVo<Object> testCROS(HttpServletResponse res) {
    // res.setHeader("Access-Control-Allow-Origin", "*");
    // res.setHeader("Access-Control-Allow-Methods", "*");
    // res.setHeader("Access-Control-Allow-Headers", "*");
    Log.info("userController");
    return BaseVo.success("test cros ok");
  }
  
  @GetMapping("/test")
  public BaseVo<Object> test(HttpServletResponse res) {
    // res.setHeader("Access-Control-Allow-Origin", "*");
    // res.setHeader("Access-Control-Allow-Methods", "*");
    // res.setHeader("Access-Control-Allow-Headers", "*");
    Log.info("userController");
    return BaseVo.success("test");
  }
  
}
