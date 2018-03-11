package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.coupon.entity.UserCoupon;
import com.feast.demo.menu.entity.CategoryMenu;
import com.feast.demo.menu.entity.Menu;
import com.feast.demo.menu.service.CategoryMenuService;
import com.feast.demo.menu.vo.DishesCategoryVo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Created by ggke on 2017/8/26.
 */
@Service
public class CouponService {

    @Autowired
    private com.feast.demo.menu.service.DishesCategoryService dishesCategoryRemoteService;

    @Autowired
    private com.feast.demo.coupon.service.CouponService couponRemoteService;

    @Autowired
    private CategoryMenuService categoryMenuRemoteService;

    @Autowired
    private com.feast.demo.menu.service.MenuService menuRemoteService;

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

    public void createCouponTemplate(CouponTemplate coupon) {
        couponRemoteService.createCouponTemplate(coupon);
    }

    public void deleteCouponTemplate(Long couponTemplateId) {
        couponRemoteService.deleteCouponTemplate(couponTemplateId);
    }

    public void updateCouponTemplate(CouponTemplate coupon) {
        couponRemoteService.updateCouponTemplate(coupon);
    }

    public ArrayList<UserCoupon> getUsedCoupon(Long storeId) {
        return couponRemoteService.getUsedCoupon(storeId);
    }

    public UserCoupon useCoupon(Long storeId, String couponCode) {
        return couponRemoteService.useCoupon(storeId,couponCode);
    }
    public void updateUserCoupon(UserCoupon userCoupon) {
        couponRemoteService.updateUserCoupon(userCoupon);
    }

    public ArrayList<CouponTemplate> queryCouponTemplateList(Long storeId) {
        return couponRemoteService.queryCouponTemplateList(storeId);
    }

    public HashMap<String,ArrayList<UserCoupon>> queryCouponList(Long userId, Integer flag) {
        System.out.println("couponRemoteService   : " + couponRemoteService);
        return couponRemoteService.queryCouponList(userId, flag);
    }

    public UserCoupon saveUserCoupon(UserCoupon userCoupon) {
        return couponRemoteService.saveUserCoupon(userCoupon);
    }

    public CouponTemplate findCouponTemplateById(Long id) {
        return couponRemoteService.findCouponTemplateById(id);
    }

}
