package com.feast.demo.menu.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.menu.vo.MenuVo;

import java.util.List;

/**
 * Created by matao on 2017/8/6.
 */
public interface MenuService {
    public MenuVo findMenuDetailByDishId(JSONObject jsonObj);

    public String getMenuCountByCategoryIdAndStoreId(JSONObject jsonObj);

    public List<MenuVo> findMenuByCategoryIdAndStoreId(JSONObject jsonObj);

    public List<MenuVo> findRecommendPrdByStoreIdAndHomeFlag(JSONObject jsonObj);
}
