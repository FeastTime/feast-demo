package com.feast.demo.menu.service;

import com.feast.demo.menu.entity.DishesCategory;

import java.util.ArrayList;

/**
 * Created by aries on 2017/8/6.
 */
public interface MenuService {
    public ArrayList<DishesCategory> findDishesCategoryByStoreid(String storeid);
}
