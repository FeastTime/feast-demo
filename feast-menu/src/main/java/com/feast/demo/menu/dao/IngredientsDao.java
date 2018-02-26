package com.feast.demo.menu.dao;

import com.feast.demo.menu.entity.Ingredients;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by matao on 2017/8/12.
 */
public interface IngredientsDao extends PagingAndSortingRepository<Ingredients,String>,IngredientsDaoCustom {
}
