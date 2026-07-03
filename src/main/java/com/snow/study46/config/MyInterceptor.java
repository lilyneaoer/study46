package com.snow.study46.config;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.snow.study46.utils.JwtUtils;
import com.snow.study46.utils.Log;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MyInterceptor implements HandlerInterceptor {
  @Autowired
  JwtUtils jwtUtils;

  private void invalidToken(HttpServletResponse response) throws Exception {
    response.setStatus(401);
    response.getWriter().write("未登录或token无效");
    Log.info("401: 未登录或token无效");
  }

  // 最常用
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // Log.info("请求快到控制器");
    // response.setHeader("Access-Control-Allow-Origin", "*");
    // response.setHeader("Access-Control-Allow-Methods", "*");
    // response.setHeader("Access-Control-Allow-Headers", "*");
    String token = request.getHeader("auth");
    if (token == null || token.equals("")) {
      invalidToken(response);
      return false;
    }
    if (jwtUtils.verifyToken(token)) {
      return true;
    }
    invalidToken(response);
    return false; // true: 通过 false: 拦截
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable ModelAndView modelAndView) throws Exception {
    // Log.info("请求到控制器了");
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable Exception ex) throws Exception {
    // Log.info("请求完成了");
    System.out.println("status: " + response.getStatus());
    System.out.println("method: " + request.getMethod());
    String queryString = request.getQueryString();
    String fullUrl = request.getRequestURL().toString();
    if (queryString != null) {
      fullUrl += "?" + queryString;
    }
    System.out.println("url: " + fullUrl);
    ContentCachingResponseWrapper wrapper = (ContentCachingResponseWrapper) response;
    byte[] content = wrapper.getContentAsByteArray();
    String body = new String(content, wrapper.getCharacterEncoding());
    System.out.println("data: " + body);
    System.out.println("======================done=================");
  }
}
