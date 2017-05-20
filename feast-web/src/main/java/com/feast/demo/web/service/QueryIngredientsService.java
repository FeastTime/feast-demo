package com.feast.demo.web.service;

import com.feast.demo.web.entity.IngredientsList;
import com.feast.demo.web.entity.IngredientsObj;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pinyou on 17-4-11.
 */

@Service
public class QueryIngredientsService {


    public IngredientsObj getIngredientsInfo(IngredientsObj ingredientsObj){



        System.out.println("androidID is:"+ingredientsObj.getAndroidID());
        System.out.println("imei is:"+ingredientsObj.getImei());
        System.out.println("ipv4 is:"+ingredientsObj.getIpv4());
        System.out.println("mac is:"+ingredientsObj.getMac());
        System.out.println("dishID is:"+ingredientsObj.getDishID());
        String dishID = ingredientsObj.getDishID();
        if(!"".equals(dishID)){

            if("2017052016313400000001".equals(dishID)){
                ingredientsObj.setResultCode("0");

                ArrayList ingredientsList = new ArrayList();

                IngredientsList ingredientsBean = new IngredientsList();
                ingredientsBean.setIngredientName("鸡肉");
                ingredientsBean.setNumber("2");
                ingredientsBean.setWeight("200");
                ingredientsBean.setCalories("350");
                ingredientsList.add(ingredientsBean);

                ingredientsBean = new IngredientsList();
                ingredientsBean.setIngredientName("猪肉");
                ingredientsBean.setNumber("3");
                ingredientsBean.setWeight("500");
                ingredientsBean.setCalories("550");
                ingredientsList.add(ingredientsBean);

                ingredientsBean = new IngredientsList();
                ingredientsBean.setIngredientName("白菜");
                ingredientsBean.setNumber("20");
                ingredientsBean.setWeight("200");
                ingredientsBean.setCalories("350");
                ingredientsList.add(ingredientsBean);

                ingredientsObj.setIngredientsList(ingredientsList);

            }else{
                ingredientsObj.setResultCode("1");
                ingredientsObj.setResultMsg("该菜品信息不存在！");
            }

        }else{
            ingredientsObj.setResultCode("1");
            ingredientsObj.setResultMsg("菜品ID为空！");
        }
        return ingredientsObj;
    }

}
