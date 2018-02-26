package com.feast.demo.menu.dao;


import java.util.ArrayList;

/**
 * Created by matao on 2017/8/19.
 */
public interface IngredientsDaoCustom {
    public ArrayList<?> findIngredientsByDishId(String dishId);
}
