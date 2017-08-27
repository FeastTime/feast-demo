package com.feast.demo.menu.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.menu.vo.IngredientsVo;

import java.util.List;

/**
 * Created by matao on 2017/8/27.
 */
public interface IngredientsService {
    public List<IngredientsVo> findIngredientsByDishId(JSONObject jsonObj);
}