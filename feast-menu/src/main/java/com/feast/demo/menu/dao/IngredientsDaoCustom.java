package com.feast.demo.menu.dao;

import java.util.List;

/**
 * Created by matao on 2017/8/19.
 */
public interface IngredientsDaoCustom {
    public List<?> findIngredientsByDishId(String dishId);
}
