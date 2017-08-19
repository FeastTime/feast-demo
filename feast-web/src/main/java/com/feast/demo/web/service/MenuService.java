package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.menu.vo.MenuVo;
import com.feast.demo.web.entity.DishesBean;
import com.feast.demo.web.entity.MenuObj;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matao on 2017/8/11.
 */

@Service
public class MenuService {
    @Autowired
    private com.feast.demo.menu.service.MenuService menuRemoteService;

    public MenuObj findMenuByCategoryIdAndStoreId(JSONObject jsonObj) {
        System.out.println("orderId is:" + jsonObj.getString("orderId"));
        System.out.println("categoryId is:" + jsonObj.getString("categoryId"));
        System.out.println("storeId is:" + jsonObj.getString("storeId"));
        System.out.println("pageNo is:" + jsonObj.getString("pageNo"));
        System.out.println("pageNum is:" + jsonObj.getString("pageNum"));

        MenuObj menuObj = new MenuObj();
        try {
            String count = menuRemoteService.getMenuCountByCategoryIdAndStoreId(jsonObj);
            if (!"0".equals(count)|| count != null){
                List<MenuVo> list = menuRemoteService.findMenuByCategoryIdAndStoreId(jsonObj);
                if (list != null && list.size()>0){
                    menuObj.setResultCode("0");
                    menuObj.setRecordCount(count);
                    menuObj.setTmpId("A");
                    DishesBean dishesBean = null;
                    ArrayList<DishesBean> dishesList = Lists.newArrayList();
                    for (int i = 0; i < list.size(); i++) {
                        MenuVo menuVo = (MenuVo) list.get(i);
                        dishesBean = new DishesBean();
                        dishesBean.setDishId(menuVo.getDishId());
                        dishesBean.setDishNo(menuVo.getDishNo());
                        dishesBean.setDishImgUrl(menuVo.getDishImgUrl());
                        dishesBean.setTvUrl(menuVo.getTvUrl());
                        dishesBean.setHotFlag(menuVo.getHotFlag());
                        dishesBean.setMaterialFlag(menuVo.getMaterialFlag());
                        dishesBean.setTitleAdImgUrl(menuVo.getTitleAdImgUrl());
                        dishesBean.setTitleAdUrl(menuVo.getTitleAdUrl());
                        dishesBean.setEatTimes(menuVo.getEatTimes());
                        dishesBean.setDishName(StringUtils.encode(menuVo.getDishName()));
                        dishesBean.setDetail(StringUtils.encode(menuVo.getDetail()));
                        dishesBean.setDiscountsTime(menuVo.getDiscountsTime());
                        dishesBean.setCost(String.valueOf(menuVo.getCost()));
                        dishesBean.setPrice(String.valueOf(menuVo.getPrice()));
                        dishesBean.setSales(menuVo.getSales());
                        dishesBean.setWaitTime(menuVo.getWaitTime());
                        dishesBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                        dishesBean.setStarLevel(menuVo.getStarLevel());
                        dishesBean.setPungencyDegree(menuVo.getPungencyDegree());
                        dishesBean.setTmpId(menuVo.getTmpId());
                        dishesBean.setPageId(menuVo.getPageId());
                        dishesList.add(dishesBean);
                    }
                    menuObj.setDishesList(dishesList);
                }else{
                    menuObj.setResultCode("0");
                    menuObj.setRecordCount("0");
                    menuObj.setDishesList(null);
                }
            }else{
                menuObj.setResultCode("0");
                menuObj.setRecordCount("0");
                menuObj.setDishesList(null);
            }
        }catch (Exception e){
            menuObj.setResultCode("1");
            e.printStackTrace();
        }
        return menuObj;
    }
}
