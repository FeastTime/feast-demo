package com.feast.demo.menu.dao;


import java.util.ArrayList;

/**
 * Created by matao on 2017/8/15.
 */
public interface MenuDaoCustom {

    public ArrayList<?> findMenuDetailByDishId(String dishId);

    public String getMenuCountByCategoryIdAndStoreId(String categoryId, String storeId);

    public ArrayList<?> findMenuByCategoryIdAndStoreId(String categoryId, String storeId, int pageNo, int pageNum);

    public String getCategoryIdStrByStoreId(String storeId) throws Exception;

    public ArrayList<?> findRecommendPrdByStoreIdAndHomeFlag(String storeId, String isHomePage, String categoryIdStr);

}
