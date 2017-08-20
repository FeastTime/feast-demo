package com.feast.demo.menu.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.menu.vo.MenuVo;

import java.util.List;

/**
 * Created by matao on 2017/8/6.
 */
public interface MenuService {


    public List<MenuVo> findMenuByCategoryIdAndStoreId(JSONObject jsonObj);

    public String getMenuCountByCategoryIdAndStoreId(JSONObject jsonObj);
}
