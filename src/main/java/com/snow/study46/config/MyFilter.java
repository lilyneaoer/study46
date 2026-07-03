package com.snow.study46.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.snow.study46.utils.Log;

@Component
@Order(1) // 定义过滤器的执行顺序，数字越小优先级越高
// @WebFilter(urlPatterns = "/user/*") // 过滤器的生效范围
public class MyFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) {
    Log.info("filter init");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    Log.info("My Filter");
    HttpServletRequest httpReq = (HttpServletRequest) request;
    String uri = httpReq.getRequestURI();
    HttpServletResponse httpRes = (HttpServletResponse) response;
    // if (uri.equals("/user/test")) {
    // chain.doFilter(request, response); // 放行请求,否则请求无法到达后续过滤器或 Controller
    // }
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    Log.info("Filter destroy");
  }
}
