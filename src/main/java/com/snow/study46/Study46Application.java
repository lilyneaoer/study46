/*
 * @Author: maxuehao lilyneao@foxmail.com
 * @Date: 2026-07-01 16:14:02
 * @LastEditors: maxuehao lilyneao@foxmail.com
 * @LastEditTime: 2026-07-04 17:49:49
 * @Description: 文件概要说明
 */
package com.snow.study46;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
// @ServletComponentScan // 如若使用WebFilter
public class Study46Application {

	public static void main(String[] args) {
		SpringApplication.run(Study46Application.class, args);
		System.out.println("=================start================");
	}

}
