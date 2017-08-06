package com.feast.demo.menu.dao;

import com.feast.demo.menu.entity.DishesCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

/**
 * Created by aries on 2017/8/6.
 */

public interface MenuDao extends PagingAndSortingRepository<DishesCategory,Long> {

    @Query("select d from DishesCategory d where d.storeid =?1")
    ArrayList<DishesCategory> findDishesCategoryByStoreid(String storeId);
}
