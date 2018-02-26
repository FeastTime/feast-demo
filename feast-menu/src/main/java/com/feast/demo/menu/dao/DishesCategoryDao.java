package com.feast.demo.menu.dao;

import com.feast.demo.menu.entity.DishesCategory;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by matao on 2017/8/12.
 */
public interface DishesCategoryDao extends PagingAndSortingRepository<DishesCategory,String>,DishesCategoryDaoCustom {
}
