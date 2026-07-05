package com.snow.study46.controller;

import java.util.Base64;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.snow.study46.model.vo.BaseVo;
import com.snow.study46.utils.Log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/test")
public class TestController {
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

  @PostMapping("/base64")
  public String testBase64(@RequestBody String str) {
    String baseStr = Base64.getEncoder().encodeToString(str.getBytes());
    return baseStr;
  }

  @PostMapping("/decodeBase64")
  public String testDecodeBase64(@RequestBody String str) {
    byte[] byteArr = Base64.getDecoder().decode(str);
    return new String(byteArr);
  }
}
