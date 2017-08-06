package com.feast.demo.menu.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.menu.dao.MenuDao;
import com.feast.demo.menu.entity.DishesCategory;
import com.feast.demo.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * Created by matao on 2017/8/6.
 */
@Service()
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;


    public ArrayList<DishesCategory> findDishesCategoryByStoreid(String storeid) {
        if(StringUtils.isNotEmpty(storeid)){
            return menuDao.findDishesCategoryByStoreid(storeid);
        }
        return null;
    }
}
