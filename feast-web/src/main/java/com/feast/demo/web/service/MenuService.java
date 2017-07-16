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
//        if ("13388996666".equals(mobileNO)) {
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
                DishesBean.setDishImgUrl("https://note.youdao.com/yws/api/group/47100452/file/142972465?method=getImage&WLP=true&width=640&height=640&version=1&cstk=QXu4Rr3S");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://guanmin.jpg");
                DishesBean.setTitleADUrl("http://guanmin.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("橄榄菜豌豆炒鸡柳"));
                DishesBean.setDetail(StringUtils.encode("其实将橄榄菜炒四季豆换为豌豆亦可，豌豆更香甜，加入鸡柳就成为一道很好下饭的家常小菜。"));
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("33.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("3");
                DishesBean.setPungencyDegree("5");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);

                DishesBean = new DishesList();

                DishesBean.setDishID("00000002");
                DishesBean.setDishNO("000000002");
                DishesBean.setDishImgUrl("");
                DishesBean.setTvUrl("glcwdcjl.mp4");
                DishesBean.setHotFlag("0");
                DishesBean.setMaterialFlag("0");
                DishesBean.setTitleADImgUrl("http://aaa.jpg");
                DishesBean.setTitleADUrl("http://aaa.html");
                DishesBean.setEatTimes("4");
                DishesBean.setDishName(StringUtils.encode("虾酱荷兰豆炒鲜鱿"));
                DishesBean.setDetail(StringUtils.encode("其实将橄榄菜炒四季豆换为豌豆亦可，豌豆更香甜，加入鸡柳就成为一道很好下饭的家常小菜。"));
                DishesBean.setTime("11:00-13:00");
                DishesBean.setCost("45.00");
                DishesBean.setPrice("33.00");
                DishesBean.setCstFlag("1");
                DishesBean.setSales("14");
                DishesBean.setWaitTime("5");
                DishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                DishesBean.setStarlevel("5");
                DishesBean.setPungencyDegree("4");
                DishesBean.setTmpId("000000000201");
                DishesBean.setPageId("1");
                dishesList.add(DishesBean);

                menuObj.setDishesList(dishesList);

            } else if ("1002".equals(classType)) {
                menuObj.setResultCode("0");
            }else if ("1003".equals(classType)) {
                menuObj.setResultCode("0");
            }else if ("1004".equals(classType)) {
                menuObj.setResultCode("0");
            }

//        } else if ("13388998888".equals(mobileNO)) {
//            {
//                menuObj.setResultCode("0");
//            }
//        }

        return menuObj;
    }
}
