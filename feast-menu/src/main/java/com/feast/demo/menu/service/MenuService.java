package com.feast.demo.menu.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.menu.entity.Menu;
import com.feast.demo.menu.vo.MenuVo;

import java.util.ArrayList;

/**
 * Created by matao on 2017/8/6.
 */
public interface MenuService {
    public MenuVo findMenuDetailByDishId(JSONObject jsonObj);

    public String getMenuCountByCategoryIdAndStoreId(JSONObject jsonObj);

    public ArrayList<MenuVo> findMenuByCategoryIdAndStoreId(JSONObject jsonObj);

    public ArrayList<MenuVo> findRecommendPrdByStoreIdAndHomeFlag(JSONObject jsonObj);

    public ArrayList<Menu> findByIds(Iterable<String> ids);
}
