package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.web.entity.DishesList;
import com.feast.demo.web.entity.MenuObj;
import com.feast.demo.web.entity.UserObj;
import com.feast.demo.web.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by pinyou on 17-4-11.
 */

@Service
public class MenuService {

    public MenuObj getMenusInfo(JSONObject jsonObj) {

        System.out.println("androidID is:"+jsonObj.getString("androidID"));
        System.out.println("imei is:"+jsonObj.getString("imei"));
        System.out.println("ipv4 is:"+jsonObj.getString("ipv4"));
        System.out.println("mac is:"+jsonObj.getString("mac"));
        System.out.println("mobileNO is:"+jsonObj.getString("mobileNO"));
        System.out.println("token is:"+jsonObj.getString("token"));
        System.out.println("orderID is:"+jsonObj.getString("orderID"));
        System.out.println("classType is:"+jsonObj.getString("classType"));
        System.out.println("page is:"+jsonObj.getString("page"));


        String mobileNO = jsonObj.getString("mobileNO");

        MenuObj menuObj = new MenuObj();
        if ("13388996666".equals(mobileNO)) {
            /**
             * 1001 鸡肉
             * 1002 牛肉
             * 1003 猪肉
             * 1004 鱼类
             */
            String classType = jsonObj.getString("classType");
            if ("1001".equals(classType)) {
                String page = jsonObj.getString("page");
                menuObj.setResultCode("0");

                menuObj.setTmpId("A");
                ArrayList dishesList = new ArrayList();
                DishesList DishesBean = new DishesList();

                DishesBean.setDishID("00000001");
                DishesBean.setDishNO("000000001");
                DishesBean.setDishImgUrl("http://aa.jpg");
                DishesBean.setTvUrl("http://aaa.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://aaa.jpg");
                DishesBean.setTitleADUrl("http://aaa.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("回锅肉"));
                DishesBean.setDetail(StringUtils.encode("回锅肉是一种烹调猪肉的四川传统菜式，属于川菜系。起源四川农村地区[1]  。古代时期称作油爆锅；四川地区大部分家庭都能制作。回锅肉的特点是：口味独特，色泽红亮，肥而不腻，入口浓香。所谓回锅，就是再次烹调的意思。回锅肉在川菜中的地位是非常重要的。回锅肉一直被认为是川菜之首，川菜之化身，提到川菜必然想到回锅肉。它色香味俱全，颜色养眼，是下饭菜之首选。配料各有不同，除了蒜苗（青蒜）还可以用彩椒，洋葱，韭菜，锅盔等来制作回锅肉，每家都有自己的秘方"));
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("33.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);

                menuObj.setDishesList(dishesList);

            } else if ("002".equals(classType)) {
                menuObj.setResultCode("0");
            }

        } else if ("13388998888".equals(mobileNO)) {
            {
                menuObj.setResultCode("0");
            }
        }

        return menuObj;
    }
}
