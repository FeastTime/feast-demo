package com.feast.demo.menu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.menu.dao.CategoryMenuDao;
import com.feast.demo.menu.entity.CategoryMenu;
import com.feast.demo.menu.service.CategoryMenuService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ggke on 2017/9/13.
 */

@Service()
public class CategoryMenuServiceImpl implements CategoryMenuService{

    @Resource
    private CategoryMenuDao categoryMenuDao;

    public List<CategoryMenu> findByCategoryId(String categoryId){
        return categoryMenuDao.findByCategoryId(categoryId);
    }
}
