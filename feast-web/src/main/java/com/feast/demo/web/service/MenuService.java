package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.menu.vo.MenuVo;
import com.feast.demo.web.entity.MenuBean;
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
                    MenuBean menuBean = null;
                    ArrayList<MenuBean> dishesList = Lists.newArrayList();
                    for (int i = 0; i < list.size(); i++) {
                        MenuVo menuVo = (MenuVo) list.get(i);
                        menuBean = new MenuBean();
                        menuBean.setDishId(menuVo.getDishId());
                        menuBean.setDishNo(menuVo.getDishNo());
                        menuBean.setDishImgUrl(menuVo.getDishImgUrl());
                        menuBean.setTvUrl(menuVo.getTvUrl());
                        menuBean.setHotFlag(menuVo.getHotFlag());
                        menuBean.setMaterialFlag(menuVo.getMaterialFlag());
                        menuBean.setTitleAdImgUrl(menuVo.getTitleAdImgUrl());
                        menuBean.setTitleAdUrl(menuVo.getTitleAdUrl());
                        menuBean.setEatTimes(menuVo.getEatTimes());
                        menuBean.setDishName(StringUtils.encode(menuVo.getDishName()));
                        menuBean.setDetail(StringUtils.encode(menuVo.getDetail()));
                        menuBean.setDiscountsTime(menuVo.getDiscountsTime());
                        menuBean.setCost(String.valueOf(menuVo.getCost()));
                        menuBean.setPrice(String.valueOf(menuVo.getPrice()));
                        menuBean.setSales(menuVo.getSales());
                        menuBean.setWaitTime(menuVo.getWaitTime());
                        menuBean.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                        menuBean.setStarLevel(menuVo.getStarLevel());
                        menuBean.setPungencyDegree(menuVo.getPungencyDegree());
                        menuBean.setTmpId(menuVo.getTmpId());
                        menuBean.setPageId(menuVo.getPageId());
                        menuBean.setCategoryId(menuVo.getCategoryId());
                        dishesList.add(menuBean);
                    }
                    menuObj.setMenuList(dishesList);
                }else{
                    menuObj.setResultCode("0");
                    menuObj.setRecordCount("0");
                    menuObj.setMenuList(null);
                }
            }else{
                menuObj.setResultCode("0");
                menuObj.setRecordCount("0");
                menuObj.setMenuList(null);
            }
        }catch (Exception e){
            menuObj.setResultCode("1");
            e.printStackTrace();
        }
        return menuObj;
    }
}
