package com.feast.demo.menu.dao;

import java.util.List;

/**
 * Created by matao on 2017/8/19.
 */
public interface DishesCategoryDaoCustom {
    public List<?> findDishesCategoryByStoreId(String storeId);
}
