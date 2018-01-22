package com.feast.demo.menu.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.menu.dao.IngredientsDao;
import com.feast.demo.menu.service.IngredientsService;
import com.feast.demo.menu.vo.IngredientsVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by matao on 2017/8/27.
 */
@Service()
public class IngredientsServiceImpl implements IngredientsService {
    @Autowired
    private IngredientsDao ingredientsDao;

    public List<IngredientsVo> findIngredientsByDishId(JSONObject jsonObj) {
        String dishId = jsonObj.getString("dishId");
        if(StringUtils.isNotEmpty(dishId)){
            List<?> result = ingredientsDao.findIngredientsByDishId(dishId);
            List<IngredientsVo> list = Lists.newArrayList();
            for(Object o:result){
                IngredientsVo vo = convertIngredientsVo((Object[]) o);//查询结果set到vo上
                list.add(vo);
            }
            return list;
        }else {
            return null;
        }
    }
    private IngredientsVo convertIngredientsVo(Object[] o){
        IngredientsVo vo = new IngredientsVo();
        vo.setIngredientName((String) o[0]);
        vo.setNumber((String) o[1]);
        vo.setWeight((String) o[2]);
        vo.setCalories((String) o[3]);
        return vo;
    }
}
