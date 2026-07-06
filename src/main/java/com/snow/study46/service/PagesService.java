package com.snow.study46.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snow.study46.model.entity.Modules;
import com.snow.study46.model.vo.BaseVo;
import com.snow.study46.repository.ModulesRepository;
import com.snow.study46.repository.PagesRepository;

@Service
public class PagesService {
  @Autowired
  ModulesRepository modulesRepository;
  @Autowired
  PagesRepository pagesRepository;

  public BaseVo<Object> getList() {
    List<Modules> modulesList = modulesRepository.selectList(null);
    return BaseVo.success(null, "查询成功");
  }

}
