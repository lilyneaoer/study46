package com.snow.study46.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snow.study46.model.vo.BaseVo;
import com.snow.study46.model.vo.ModulesVo;
import com.snow.study46.service.PagesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/pages")
public class PageController {
  @Autowired
  PagesService pagesService;

  @GetMapping("/list")
  public BaseVo<List<ModulesVo>> getList() {
    return pagesService.getListTree();
  }

}
