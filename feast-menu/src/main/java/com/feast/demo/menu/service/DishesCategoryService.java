package com.feast.demo.menu.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.menu.entity.DishesCategory;
import com.feast.demo.menu.vo.DishesCategoryVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matao on 2017/8/12.
 */
public interface DishesCategoryService {
    public List<DishesCategoryVo> findDishesCategoryByStoreId(JSONObject jsonObj);
}
