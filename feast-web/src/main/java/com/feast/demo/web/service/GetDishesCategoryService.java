package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.entity.DishesCategoryList;
import com.feast.demo.web.entity.DishesCategoryObj;
import com.feast.demo.web.entity.IngredientsList;
import com.feast.demo.web.entity.IngredientsObj;
import com.feast.demo.web.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by pinyou on 17-4-11.
 */

@Service
public class GetDishesCategoryService {


    public DishesCategoryObj getDishesCategoryInfo(JSONObject jsonObj) {

        System.out.println("androidID is:" + jsonObj.getString("androidID"));
        System.out.println("imei is:" + jsonObj.getString("imei"));
        System.out.println("ipv4 is:" + jsonObj.getString("ipv4"));
        System.out.println("mac is:" + jsonObj.getString("mac"));

        DishesCategoryObj dishesCategoryObj = new DishesCategoryObj();

        dishesCategoryObj.setResultCode("0");

        ArrayList dishesCategoryList = new ArrayList();

        DishesCategoryList dishesCategoryBean = new DishesCategoryList();
        dishesCategoryBean.setCategoryName(StringUtils.encode("今日特价"));
        dishesCategoryBean.setClassType("1000");
        dishesCategoryList.add(dishesCategoryBean);

        dishesCategoryBean = new DishesCategoryList();
        dishesCategoryBean.setCategoryName(StringUtils.encode("羊肉"));
        dishesCategoryBean.setClassType("1005");
        dishesCategoryList.add(dishesCategoryBean);

        dishesCategoryBean = new DishesCategoryList();
        dishesCategoryBean.setCategoryName(StringUtils.encode("牛肉"));
        dishesCategoryBean.setClassType("1002");
        dishesCategoryList.add(dishesCategoryBean);

        dishesCategoryBean = new DishesCategoryList();
        dishesCategoryBean.setCategoryName(StringUtils.encode("猪肉"));
        dishesCategoryBean.setClassType("1003");
        dishesCategoryList.add(dishesCategoryBean);

        dishesCategoryBean = new DishesCategoryList();
        dishesCategoryBean.setCategoryName(StringUtils.encode("鸡肉"));
        dishesCategoryBean.setClassType("1001");
        dishesCategoryList.add(dishesCategoryBean);

        dishesCategoryBean = new DishesCategoryList();
        dishesCategoryBean.setCategoryName(StringUtils.encode("鱼肉"));
        dishesCategoryBean.setClassType("1001");
        dishesCategoryList.add(dishesCategoryBean);

        dishesCategoryObj.setDishesCategoryList(dishesCategoryList);

        return dishesCategoryObj;
    }

}
