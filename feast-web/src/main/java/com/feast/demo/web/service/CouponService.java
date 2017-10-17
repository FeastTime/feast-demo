package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.coupon.entity.Coupon;
import com.feast.demo.menu.entity.CategoryMenu;
import com.feast.demo.menu.entity.Menu;
import com.feast.demo.menu.service.CategoryMenuService;
import com.feast.demo.menu.vo.DishesCategoryVo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ggke on 2017/8/26.
 */

@org.springframework.stereotype.Service
public class CouponService {

    @Autowired
    private com.feast.demo.menu.service.DishesCategoryService dishesCategoryRemoteService;

    @Resource
    private com.feast.demo.coupon.service.CouponService couponRemoteService;

    @Resource
    private CategoryMenuService categoryMenuRemoteService;

    @Autowired
    private com.feast.demo.menu.service.MenuService menuRemoteService;

    public List<Coupon> findAllCoupon(){
        return couponRemoteService.findAll();
    }

    public List<Menu> findCouponDishes(String storeId){
        JSONObject json = new JSONObject();
        json.put("storeId",storeId);
        List<DishesCategoryVo> categoryVos = dishesCategoryRemoteService.findDishesCategoryByStoreId(json);
        boolean hasCoupon = false;
        DishesCategoryVo couponDishesCategory = null;
        for(DishesCategoryVo vo:categoryVos){
            if(vo.getCategoryName().equals("抽奖")){
                hasCoupon = true;
                couponDishesCategory = vo;
                break;
            }
        }
        if(!hasCoupon){
            return Lists.newArrayList();
        }
        List<CategoryMenu> categoryMenus = categoryMenuRemoteService.findByCategoryId(couponDishesCategory.getCategoryId());
        List<String> dishIds = Lists.newArrayList();
        for(CategoryMenu cm:categoryMenus){
            dishIds.add(cm.getDishid());
        }
        if(CollectionUtils.isEmpty(dishIds)){
            return Lists.newArrayList();
        }

       return menuRemoteService.findByIds(dishIds);

    }

}
