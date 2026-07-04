/*
 * @Author: maxuehao lilyneao@foxmail.com
 * @Date: 2026-07-03 19:48:04
 * @LastEditors: maxuehao lilyneao@foxmail.com
 * @LastEditTime: 2026-07-04 22:09:58
 * @Description: 文件概要说明
 */
package com.snow.study46.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.snow.study46.entity.User;
import com.snow.study46.model.dto.*;
import com.snow.study46.model.vo.*;
import com.snow.study46.service.UserService;
import com.snow.study46.utils.Log;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.imageio.ImageIO;

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
  DefaultKaptcha defaultKaptcha;
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
    return userService.login(form, session);
  }

  @PostMapping("/modifyPassword")
  public BaseVo<Object> modifyPassword(@RequestBody ModifyPasswordDTO form) {
    return userService.modifyUserPassword(form);
  }

  // @PostMapping("/remove")
  // public BaseVo<Object> remove(@RequestBody RegisterDTO form) {
  // return userService.removeUser(form);
  // }

  @PostMapping("/removeUser")
  public BaseVo<Object> removeUser(@RequestBody UserIdDTO id) {
    return userService.removeUserById(id);
  }

  @GetMapping("/getCode")
  public String getCode(HttpSession session) throws IOException {
    // 生成图片验证码
    String verifyCode = defaultKaptcha.createText();
    session.setAttribute("verifyCode", verifyCode);
    BufferedImage image =  defaultKaptcha.createImage(verifyCode);
    ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
    ImageIO.write(image, "jpg", byteOutput);
    byte[] imageByteArr = byteOutput.toByteArray();
    String base64ImStr = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageByteArr);
    return base64ImStr;
  }
}
