package com.snow.study46.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.snow.study46.model.entity.Modules;
import com.snow.study46.model.entity.Pages;
import com.snow.study46.model.vo.BaseVo;
import com.snow.study46.model.vo.ModulesVo;
import com.snow.study46.model.vo.PageDetailVo;
import com.snow.study46.repository.ModulesRepository;
import com.snow.study46.repository.PagesRepository;

@Service
public class PagesService {
  private final ModulesRepository modulesRepository;
  private final PagesRepository pagesRepository;

  public PagesService(ModulesRepository modulesRepository, PagesRepository pagesRepository) {
    this.modulesRepository = modulesRepository;
    this.pagesRepository = pagesRepository;
  }

  /**
   * 将 Pages 实体转换为 PageDetailVo，并递归构建子节点
   */
  private PageDetailVo createPageDetailVo(Pages page, List<Pages> pageList) {
    PageDetailVo vo = new PageDetailVo();
    vo.setPageId(page.getPageId());
    vo.setPageName(page.getPageName());
    vo.setPagePath(page.getPagePath());
    vo.setParentId(page.getParentId());
    vo.setModuleId(page.getModuleId());
    vo.setChildren(findChildren(pageList, page));
    return vo;
  }

  /**
   * 递归查找指定页面的子页面
   */
  private List<PageDetailVo> findChildren(List<Pages> pageList, Pages parent) {
    return pageList.stream()
        .filter(page -> page.getParentId() == parent.getPageId())
        .map(page -> createPageDetailVo(page, pageList))
        .collect(Collectors.toList());
  }

  public BaseVo<List<ModulesVo>> getList() {
    List<Modules> modulesList = modulesRepository.selectList(null);
    List<Pages> pagesList = pagesRepository.selectList(null);

    List<ModulesVo> modulesVoList = modulesList.stream().map(module -> {
      ModulesVo modulesVo = new ModulesVo();
      modulesVo.setModuleId(module.getModuleId());
      modulesVo.setModuleName(module.getModuleName());
      List<PageDetailVo> pageVoList = pagesList.stream()
          .filter(page -> page.getModuleId() == module.getModuleId() && page.getParentId() == 0)
          .map(page -> createPageDetailVo(page, pagesList))
          .collect(Collectors.toList());
      modulesVo.setPage(pageVoList);
      return modulesVo;
    }).collect(Collectors.toList());

    return BaseVo.success(modulesVoList, "查询成功");
  }
}
