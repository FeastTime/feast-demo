package com.feast.demo.menu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.menu.dao.TMenuDao;
import com.feast.demo.menu.entity.TMenu;
import com.feast.demo.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ggke on 2017/4/4.
 */
@Service()
public class MenuServiceImpl implements MenuService {

    @Autowired
    private TMenuDao tMenuDao;

    public String getStatus() {
        return "from menu service.ok!";
    }

    public List<TMenu> findAll() {
        return (List<TMenu>) tMenuDao.findAll();
    }
}
