package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.entity.DishesCategoryObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by aries on 17-8-6.
 */

@Service
public class DishesCategoryService {

    @Autowired
    private com.feast.demo.menu.service.MenuService menuRemoteService;

    public DishesCategoryObj findDishesCategoryByStoreid(JSONObject jsonObj) {
        String  StoreId= jsonObj.getString("storeId");
        DishesCategoryObj dishesCategoryObj = new DishesCategoryObj();
//        ArrayList<DishesCategory> dishesCategoryList = menuRemoteService.findDishesCategoryByStoreid(StoreId);
//        List<DishesCategoryList> dishesCategoryBeanList = Lists.newArrayList();
//        try {
//            if (dishesCategoryList != null && dishesCategoryList.size() > 0) {
//                dishesCategoryObj.setResultCode("0");
//                for (int i = 0; i < dishesCategoryList.size(); i++) {
//                    DishesCategory dc = dishesCategoryList.get(i);
//                    DishesCategoryList dishesCategoryListBean = new DishesCategoryList();
//                    dishesCategoryListBean.setCategoryName(dc.getCategoryname());
//                    dishesCategoryListBean.setCategoryId(dc.getCategoryid()+"");
//                    dishesCategoryBeanList.add(dishesCategoryListBean);
//
//                }
//                dishesCategoryObj.setDishesCategoryList((ArrayList) dishesCategoryBeanList);
//            } else {
//                dishesCategoryObj.setResultCode("0");
//                dishesCategoryObj.setDishesCategoryList(null);
//            }
//        }catch (Exception e){
//            dishesCategoryObj.setResultCode("1");
//            e.printStackTrace();
//        }
        return dishesCategoryObj;
    }

}
