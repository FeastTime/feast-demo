package com.feast.demo.menu.dao;

import com.feast.demo.menu.entity.CategoryMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by ggke on 2017/9/13.
 */
public interface CategoryMenuDao extends PagingAndSortingRepository<CategoryMenu,String>{

    @Query("select c from CategoryMenu c where c.categoryid=?1")
    List<CategoryMenu> findByCategoryId(String categoryId);
}
