package com.feast.demo.menu.dao;

import java.util.List;

/**
 * Created by aries on 2017/8/12.
 */
public interface MenuDao {

    public String getMenuCountByCategoryIdAndStoreId(String categoryId, String storeId);

    List<?> findMenuByCategoryIdAndStoreId(String categoryId, String storeId, int pageNo, int pageNum);
}
