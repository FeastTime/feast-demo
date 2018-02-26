package com.feast.demo.menu.service.impl;

import org.springframework.stereotype.Service;
import com.feast.demo.menu.dao.CategoryMenuDao;
import com.feast.demo.menu.entity.CategoryMenu;
import com.feast.demo.menu.service.CategoryMenuService;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by ggke on 2017/9/13.
 */

@Service()
public class CategoryMenuServiceImpl implements CategoryMenuService{

    @Resource
    private CategoryMenuDao categoryMenuDao;

    public ArrayList<CategoryMenu> findByCategoryId(String categoryId){
        return categoryMenuDao.findByCategoryId(categoryId);
    }
}
