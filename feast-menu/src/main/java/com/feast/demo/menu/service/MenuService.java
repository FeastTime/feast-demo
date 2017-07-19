package com.feast.demo.menu.service;

import com.feast.demo.menu.entity.TMenu;

import java.util.List;

/**
 * Created by ggke on 2017/4/4.
 */
public interface MenuService {
    public String getStatus();

    public List<TMenu> findAll();
}
