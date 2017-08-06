package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.menu.entity.DishesCategory;
import com.feast.demo.web.entity.DishesCategoryList;
import com.feast.demo.web.entity.DishesCategoryObj;
import com.feast.demo.web.entity.IngredientsList;
import com.feast.demo.web.entity.IngredientsObj;
import com.feast.demo.web.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by aries on 17-8-6.
 */

@Service
public class DishesCategoryService {

    @Autowired
    private com.feast.demo.menu.service.MenuService menuRemoteService;

    public DishesCategoryObj findDishesCategoryByStoreid(JSONObject jsonObj) {
        String  StoreId= jsonObj.getString("storeID");
        ArrayList dishesCategoryList = menuRemoteService.findDishesCategoryByStoreid(StoreId);
        DishesCategoryObj dishesCategoryObj = new DishesCategoryObj();
        if (dishesCategoryList != null && dishesCategoryList.size()>0){
            dishesCategoryObj.setResultCode("0");
            DishesCategoryList dishesCategoryBean =null;
            for(int i=0; i<dishesCategoryList.size(); i++){
                DishesCategory dc = (DishesCategory) dishesCategoryList.get(i);
                dishesCategoryBean = new DishesCategoryList();
                dishesCategoryBean.setCategoryName(StringUtils.encode(dc.getCategoryname()));
                dishesCategoryBean.setCategoryId(String.valueOf(dc.getCategoryid()));
                dishesCategoryList.add(dishesCategoryBean);

            }
            dishesCategoryObj.setDishesCategoryList(dishesCategoryList);
        }else{
            dishesCategoryObj.setResultCode("1");
            dishesCategoryObj.setDishesCategoryList(null);
        }
        return dishesCategoryObj;
    }

}
