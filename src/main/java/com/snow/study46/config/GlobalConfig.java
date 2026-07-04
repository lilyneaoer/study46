package com.snow.study46.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalConfig implements WebMvcConfigurer {
  // 跨域设置
  @Override
  public void addCorsMappings(@NonNull CorsRegistry registry) {
    // 一般情况
    // registry.addMapping("/**")
    // .allowedOrigins("*")
    // .allowedMethods("*")
    // .allowedHeaders("*");

    // 允许请求携带cookie
    registry.addMapping("/**")
        .allowCredentials(true)
        .allowedOrigins("http://localhost:5173")
        .allowedMethods("*");
  }

  // 注册拦截器
  @Autowired
  MyInterceptor myInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(myInterceptor).excludePathPatterns(
        // 排除路径, 不进行拦截
        "/user/login",
        "/user/register",
        "/user/getCode",
        "/user/test*",
        "/test/*"
        );
  }
}
