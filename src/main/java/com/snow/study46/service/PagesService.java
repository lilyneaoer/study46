package com.snow.study46.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snow.study46.model.entity.Module;
import com.snow.study46.model.entity.Page;
import com.snow.study46.model.vo.BaseVo;
import com.snow.study46.model.vo.ModulesVo;
import com.snow.study46.model.vo.PageDetailVo;
import com.snow.study46.repository.ModulesRepository;
import com.snow.study46.repository.PagesRepository;
import com.snow.study46.repository.RolePageRepository;
import com.snow.study46.repository.RoleRepository;
import com.snow.study46.repository.UserRepository;
import com.snow.study46.repository.UserRoleRepository;
import com.snow.study46.utils.Log;

@Service
public class PagesService {

  @Autowired
  ModulesRepository modulesRepository;
  @Autowired
  PagesRepository pagesRepository;
  @Autowired
  RoleRepository roleRepository;
  @Autowired
  RolePageRepository rolePageRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  UserRoleRepository userRoleRepository;

  @Autowired

  // 查询页面树
  public BaseVo<List<ModulesVo>> getListTree() {
    List<Module> moduleList = modulesRepository.selectList(null);
    List<ModulesVo> resultList = moduleList.stream().map((module) -> {
      ModulesVo modulesVo = new ModulesVo();
      modulesVo.setModuleId(module.getModuleId());
      modulesVo.setModuleName(module.getModuleName());
      // QueryWrapper<Page> pagQueryWrapper = new QueryWrapper<Page>();
      // pagQueryWrapper.eq("module_id", module.getModuleId()).isNull("parent_id");
      LambdaQueryWrapper<Page> queryWrapper = new LambdaQueryWrapper<>();
      // queryWrapper.eq(p -> p.getModuleId(), module.getModuleId()).isNull(p ->
      // p.getParentId());
      queryWrapper.eq(Page::getModuleId, module.getModuleId()).isNull(Page::getParentId);
      List<Page> listPage = pagesRepository.selectList(queryWrapper);
      modulesVo.setPage(getChildPages(listPage));
      return modulesVo;
    }).collect(Collectors.toList());
    return BaseVo.success(resultList, "查询成功");
  }

  // 查询子页面
  public List<PageDetailVo> getChildPages(List<Page> listPage) {
    List<PageDetailVo> pageDetailVoList = new ArrayList<>();
    pageDetailVoList = listPage.stream().map((pageItem) -> {
      PageDetailVo pageDetailVo = new PageDetailVo();
      pageDetailVo.setPageId(pageItem.getPageId());
      pageDetailVo.setPageName(pageItem.getPageName());
      pageDetailVo.setPageName(pageItem.getPageName());
      pageDetailVo.setPagePath(pageItem.getPagePath());
      LambdaQueryWrapper<Page> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(Page::getParentId, pageItem.getPageId());
      List<Page> childPages = pagesRepository.selectList(queryWrapper);
      if (childPages.size() != 0) {
        pageDetailVo.setChildren(getChildPages(childPages));
      } else {
        pageDetailVo.setChildren(new ArrayList<PageDetailVo>());
      }
      return pageDetailVo;
    }).collect(Collectors.toList());
    return pageDetailVoList;
  };

  // 通过userId查询页面
  public BaseVo<Object> getPagesByUserId(String userId) {
    return BaseVo.success(null, "查询成功");
  }

  // /**
  // * 将 Pages 实体转换为 PageDetailVo，并递归构建子节点
  // */
  // private PageDetailVo createPageDetailVo(Pages page, List<Pages> pageList) {
  // PageDetailVo vo = new PageDetailVo();
  // vo.setPageId(page.getPageId());
  // vo.setPageName(page.getPageName());
  // vo.setPagePath(page.getPagePath());
  // vo.setParentId(page.getParentId());
  // vo.setModuleId(page.getModuleId());
  // vo.setChildren(findChildren(pageList, page));
  // return vo;
  // }

  // /**
  // * 递归查找指定页面的子页面
  // */
  // private List<PageDetailVo> findChildren(List<Pages> pageList, Pages parent) {
  // return pageList.stream()
  // .filter(page -> page.getParentId() == parent.getPageId())
  // .map(page -> createPageDetailVo(page, pageList))
  // .collect(Collectors.toList());
  // }

  // public BaseVo<List<ModulesVo>> getList() {
  // List<Modules> modulesList = modulesRepository.selectList(null);
  // List<Pages> pagesList = pagesRepository.selectList(null);

  // List<ModulesVo> modulesVoList = modulesList.stream().map(module -> {
  // ModulesVo modulesVo = new ModulesVo();
  // modulesVo.setModuleId(module.getModuleId());
  // modulesVo.setModuleName(module.getModuleName());
  // List<PageDetailVo> pageVoList = pagesList.stream()
  // .filter(page -> page.getModuleId() == module.getModuleId() &&
  // page.getParentId() == 0)
  // .map(page -> createPageDetailVo(page, pagesList))
  // .collect(Collectors.toList());
  // modulesVo.setPage(pageVoList);
  // return modulesVo;
  // }).collect(Collectors.toList());

  // return BaseVo.success(modulesVoList, "查询成功");
  // }
}
