package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.entity.IngredientsList;
import com.feast.demo.web.entity.IngredientsObj;
import com.feast.demo.web.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by pinyou on 17-4-11.
 */

@Service
public class QueryIngredientsService {


    public IngredientsObj getIngredientsInfo(JSONObject jsonObj){

        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("dishID is:"+jsonObj.getString("dishID"));

        String dishID = jsonObj.getString("dishID");
        IngredientsObj ingredientsObj = new IngredientsObj();
        if(!"".equals(dishID)){

            if("2017052016313400000001".equals(dishID)){
                ingredientsObj.setResultCode("0");

                ArrayList ingredientsList = new ArrayList();

                IngredientsList ingredientsBean = new IngredientsList();
                ingredientsBean.setIngredientName(StringUtils.encode("鸡肉"));
                ingredientsBean.setNumber("2");
                ingredientsBean.setWeight("200");
                ingredientsBean.setCalories("350");
                ingredientsList.add(ingredientsBean);

                ingredientsBean = new IngredientsList();
                ingredientsBean.setIngredientName(StringUtils.encode("猪肉"));
                ingredientsBean.setNumber("3");
                ingredientsBean.setWeight("500");
                ingredientsBean.setCalories("550");
                ingredientsList.add(ingredientsBean);

                ingredientsBean = new IngredientsList();
                ingredientsBean.setIngredientName(StringUtils.encode("白菜"));
                ingredientsBean.setNumber("20");
                ingredientsBean.setWeight("200");
                ingredientsBean.setCalories("350");
                ingredientsList.add(ingredientsBean);

                ingredientsObj.setIngredientsList(ingredientsList);

            }else{
                ingredientsObj.setResultCode("1");
                ingredientsObj.setResultMsg(StringUtils.encode("该菜品信息不存在！"));
            }

        }else{
            ingredientsObj.setResultCode("1");
            ingredientsObj.setResultMsg(StringUtils.encode("菜品ID为空！"));
        }
        return ingredientsObj;
    }

}
