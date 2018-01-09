package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.menu.vo.IngredientsVo;
import com.feast.demo.web.entity.IngredientsBean;
import com.feast.demo.web.entity.IngredientsObj;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matao on 2017/8/27.
 */

@Service
public class IngredientsService {

    @Autowired
    private com.feast.demo.menu.service.IngredientsService ingredientsRemoteService;

    public IngredientsObj findIngredientsByDishId(JSONObject jsonObj) {
        System.out.println("dishId is:" + jsonObj.getString("dishId")+"===========");

        IngredientsObj ingredientsObj = new IngredientsObj();
        try {
            List<IngredientsVo> list = ingredientsRemoteService.findIngredientsByDishId(jsonObj);
            if (list != null && list.size() > 0) {
                ingredientsObj.setResultCode("0");
                IngredientsBean ingredientsBean = null;
                ArrayList<IngredientsBean> ingredientsBeanList = Lists.newArrayList();
                for (int i = 0; i < list.size(); i++) {
                    IngredientsVo ingredientsVo = (IngredientsVo) list.get(i);
                    ingredientsBean = new IngredientsBean();
                    ingredientsBean.setIngredientName(ingredientsVo.getIngredientName());
                    ingredientsBean.setNumber(ingredientsVo.getNumber());
                    ingredientsBean.setWeight(ingredientsVo.getWeight());
                    ingredientsBean.setCalories(ingredientsVo.getCalories());
                    ingredientsBeanList.add(ingredientsBean);
                }
                ingredientsObj.setIngredientsList(ingredientsBeanList);
            } else {
                ingredientsObj.setResultCode("0");
                ingredientsObj.setIngredientsList(null);
            }
        } catch (Exception e) {
            ingredientsObj.setResultCode("1");
            e.printStackTrace();
        }
        return ingredientsObj;
    }
}
