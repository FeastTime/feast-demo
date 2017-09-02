package com.feast.demo.menu.dao;

import java.util.List;

/**
 * Created by matao on 2017/8/15.
 */
public interface MenuDaoCustom {

    public String getMenuCountByCategoryIdAndStoreId(String categoryId, String storeId);

    public List<?> findMenuByCategoryIdAndStoreId(String categoryId, String storeId, int pageNo, int pageNum);

    public String getCategoryIdStrByStoreId(String storeId) throws Exception;

    public List<?> findRecommendPrdByStoreIdAndHomeFlag(String storeId, String isHomePage, String categoryIdStr);

}
