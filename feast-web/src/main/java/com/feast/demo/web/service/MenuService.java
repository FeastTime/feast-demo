package com.feast.demo.web.service;

import com.feast.demo.web.entity.DishesList;
import com.feast.demo.web.entity.MenuObj;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by pinyou on 17-4-11.
 */

@Service
public class MenuService {

    public MenuObj getMenusInfo(MenuObj menuObj) {

        System.out.println("imei is:" + menuObj.getImei());
        System.out.println("androidID is:" + menuObj.getAndroidID());
        System.out.println("ipv4 is:" + menuObj.getIpv4());
        System.out.println("mac is:" + menuObj.getMac());
        System.out.println("mobileNO is:" + menuObj.getMobileNO());
        System.out.println("token is:" + menuObj.getToken());
        System.out.println("orderID is:" + menuObj.getOrderID());
        System.out.println("classType is:" + menuObj.getClassType());
        System.out.println("page is:" + menuObj.getPage());


        String mobileNO = menuObj.getMobileNO();
        String classType = menuObj.getClassType();
        String page = menuObj.getPage();
        if ("13388996666".equals(mobileNO)) {
            /**
             * 001 鸡肉
             * 002 牛肉
             * 003 猪肉
             * 004 鱼类
             */
            if ("001".equals(classType)) {
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
                DishesBean.setDishName("回锅肉");
                DishesBean.setDetail("回锅肉是一种烹调猪肉的四川传统菜式，属于川菜系。起源四川农村地区[1]  。古代时期称作油爆锅；四川地区大部分家庭都能制作。回锅肉的特点是：口味独特，色泽红亮，肥而不腻，入口浓香。所谓回锅，就是再次烹调的意思。回锅肉在川菜中的地位是非常重要的。回锅肉一直被认为是川菜之首，川菜之化身，提到川菜必然想到回锅肉。它色香味俱全，颜色养眼，是下饭菜之首选。配料各有不同，除了蒜苗（青蒜）还可以用彩椒，洋葱，韭菜，锅盔等来制作回锅肉，每家都有自己的秘方");
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("33.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent("钠含量30克，热量50卡");
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
