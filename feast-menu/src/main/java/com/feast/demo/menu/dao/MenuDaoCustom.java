package com.feast.demo.menu.dao;

import java.util.List;

/**
 * Created by ggke on 2017/8/15.
 */
public interface MenuDaoCustom {

    public String getMenuCountByCategoryIdAndStoreId(String categoryId, String storeId);

    public List<?> findMenuByCategoryIdAndStoreId(String categoryId, String storeId, int pageNo, int pageNum);
}
