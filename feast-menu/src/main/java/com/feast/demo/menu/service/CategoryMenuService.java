package com.feast.demo.menu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.menu.entity.CategoryMenu;

import java.util.ArrayList;

/**
 * Created by ggke on 2017/9/13.
 */

@Service
public interface CategoryMenuService {

    ArrayList<CategoryMenu> findByCategoryId(String categoryId);
}
