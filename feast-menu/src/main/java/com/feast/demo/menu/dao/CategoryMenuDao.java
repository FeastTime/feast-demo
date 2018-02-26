package com.feast.demo.menu.dao;

import com.feast.demo.menu.entity.CategoryMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

/**
 * Created by ggke on 2017/9/13.
 */
public interface CategoryMenuDao extends PagingAndSortingRepository<CategoryMenu,String>{

    @Query("select c from CategoryMenu c where c.categoryid=?1")
    ArrayList<CategoryMenu> findByCategoryId(String categoryId);
}
