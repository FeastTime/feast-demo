package com.feast.demo.menu.dao;

import com.feast.demo.menu.entity.DishesCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aries on 2017/8/6.
 */
public interface DishesCategoryDao extends PagingAndSortingRepository<DishesCategory,Long> {

    @Query("select d from DishesCategory d where d.storeid =?1")
    List<DishesCategory> findDishesCategoryByStoreid(String storeId);
}
