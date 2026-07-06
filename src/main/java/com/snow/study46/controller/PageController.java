package com.snow.study46.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/pages")
public class PageController {
  @GetMapping("/list")
  public String getList() {
    return new String();
  }

}
