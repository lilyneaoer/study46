package com.snow.study46.config;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.snow.study46.utils.Log;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
  // 最常用
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // Log.info("请求快到控制器");
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "*");
    response.setHeader("Access-Control-Allow-Headers", "*");
    return true; // true: 通过 false: 拦截
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
    // System.out.println("params: " + toStringMap(request.getParameterMap()));
    ContentCachingResponseWrapper wrapper = (ContentCachingResponseWrapper) response;
    byte[] content = wrapper.getContentAsByteArray();
    String body = new String(content, wrapper.getCharacterEncoding());
    System.out.println("data: " + body);
    System.out.println("======================done=================");
  }
}
