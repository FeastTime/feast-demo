package com.feast.demo.menu.dao;

import com.feast.demo.menu.entity.Menu;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by aries on 2017/8/12.
 */
public interface MenuDao extends PagingAndSortingRepository<Menu,Long>,MenuDaoCustom {

    public String getMenuCountByCategoryIdAndStoreId(String categoryId, String storeId);

    List<?> findMenuByCategoryIdAndStoreId(String categoryId, String storeId, int pageNo, int pageNum);
}
